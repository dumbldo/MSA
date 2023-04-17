package fr.miage.dauphine.MSA.Emprunt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;
import org.apache.kafka.common.serialization.StringDeserializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;

import org.apache.kafka.common.serialization.StringSerializer;


@Service
public class EmpruntKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpruntKafkaConsumer.class);
    private static final String TOPIC = "emprunt-topic";

    @Autowired
    private EmpruntRepository empruntRepository;

    @KafkaListener(topics = TOPIC, groupId = "emprunt-group")
    public void consumeEmprunt(String empruntJson) throws JsonProcessingException {
        LOGGER.info("Received emprunt message='{}'", empruntJson);
        
        // Deserialize Emprunt from JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        Emprunt emprunt = objectMapper.readValue(empruntJson, Emprunt.class);
        
        // Check if the corresponding Livre is available before creating the Emprunt
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        String message = objectMapper.writeValueAsString(emprunt.getLivreId());
        kafkaTemplate.send("check-book-availability", message);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps());
        consumer.subscribe(Collections.singleton("book-availability-response"));

        ConsumerRecords<String, String> records = null;
        boolean isAvailable = false;
        while (!isAvailable) {
            records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                if (record.value().equals("true")) {
                    isAvailable = true;
                    break;
                }
            }
        }
        consumer.close();
        
        if (isAvailable) {
            // Save the Emprunt to the database
            empruntRepository.save(emprunt);
            LOGGER.info("Emprunt created successfully: {}", emprunt);
        } else {
            LOGGER.warn("Livre is not available for emprunt: {}", emprunt);
        }
    }

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "emprunt-consumer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }
    
    private ProducerFactory<String, String> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }
    
}
