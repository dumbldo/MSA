package fr.miage.dauphine.MSA.Emprunt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import java.util.HashMap;
import java.util.Map;

import org.springframework.kafka.support.serializer.JsonSerde;



import org.apache.kafka.common.serialization.Serdes;


@EnableKafka
@Configuration
public class KafkaProducerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
        // add any additional properties here
        return props;
    }

    @Bean
    public ProducerFactory<String, Emprunt> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs(), 
            Serdes.String().serializer(),
            new JsonSerde<>(Emprunt.class).serializer()
        );
    }

    @Bean
    public KafkaTemplate<String, Emprunt> kafkaTemplate() {
        KafkaTemplate<String, Emprunt> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        return kafkaTemplate;
    }
    
}
