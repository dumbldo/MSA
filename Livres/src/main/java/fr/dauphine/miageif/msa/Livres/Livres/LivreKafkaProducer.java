package fr.dauphine.miageif.msa.Livres.Livres;

import fr.dauphine.miageif.msa.Livres.Livre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LivreKafkaProducer {

    @Autowired
    private KafkaTemplate<String, Livre> kafkaTemplate;

    public void sendLivre(String topic, Livre livre) {
        kafkaTemplate.send(topic, livre);
    }
}
