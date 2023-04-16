package fr.dauphine.miageif.msa.Livres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class LivreKafkaConsumer {

    @Autowired
    private KafkaTemplate<String, Livre> kafkaTemplate;

    public void sendMessage(String topic, Livre livre) {
        kafkaTemplate.send(topic, livre);
    }
}
