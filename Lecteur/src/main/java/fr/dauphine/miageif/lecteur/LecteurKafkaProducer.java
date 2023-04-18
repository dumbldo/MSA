package fr.dauphine.miageif.lecteur;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LecteurKafkaProducer {

    @Autowired
    private KafkaTemplate<String, Lecteur> kafkaTemplate;

    public void sendLecteur(String topic, Lecteur lecteur) {
        kafkaTemplate.send(topic, lecteur);
    }

    public void send(String string, Lecteur existingLecteur) {
    }
}
