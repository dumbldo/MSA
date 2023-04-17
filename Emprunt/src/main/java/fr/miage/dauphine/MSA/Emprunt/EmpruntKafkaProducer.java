package fr.miage.dauphine.MSA.Emprunt;

import org.apache.kafka.common.serialization.StringDeserializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Service
public class EmpruntKafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpruntKafkaProducer.class);
    private static final String EMRPUNT_TOPIC = "emprunt-topic";
    private static final String CHECK_BOOK_AVAILABILITY_TOPIC = "check-book-availability";
    private static final String BOOK_AVAILABILITY_RESPONSE_TOPIC = "book-availability-response";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    public boolean sendEmprunt(Emprunt emprunt) throws JsonProcessingException {
        LOGGER.info("Sending emprunt message='{}' to topic='{}'", emprunt, EMRPUNT_TOPIC);

        // Send message to check book availability
        String message = objectMapper.writeValueAsString(emprunt.getLivreId());
        kafkaTemplate.send(CHECK_BOOK_AVAILABILITY_TOPIC, message);

        // Wait for response from book availability check
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps());
        consumer.subscribe(Collections.singleton(BOOK_AVAILABILITY_RESPONSE_TOPIC));

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
            // Send emprunt message if book is available
            kafkaTemplate.send(EMRPUNT_TOPIC, objectMapper.writeValueAsString(emprunt));
            LOGGER.info("Emprunt message sent successfully: {}", emprunt);
        } else {
            LOGGER.warn("Livre is not available for emprunt: {}", emprunt);
        }

        return isAvailable;
    }

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }
    

}
