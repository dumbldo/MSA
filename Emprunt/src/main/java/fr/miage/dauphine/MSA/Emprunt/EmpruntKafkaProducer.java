package fr.miage.dauphine.MSA.Emprunt;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmpruntKafkaProducer {

    @Autowired
    private KafkaTemplate<String, Emprunt> kafkaTemplate;

    public void sendEmprunt(String topic, Emprunt emprunt) {
        kafkaTemplate.send(topic, emprunt);
    }
}