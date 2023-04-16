package fr.dauphine.miageif.msa.Lecteur;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LecteurKafkaConsumer {

    @Autowired
    private LecteurService lecteurService;

    @Autowired
    private LecteurKafkaProducer lecteurKafkaProducer;

    @Autowired
    private KafkaTemplate<String, Lecteur> kafkaTemplate;
 
    public void send(String topicName, Lecteur lecteur) {
        kafkaTemplate.send(topicName, lecteur);
    }

    @KafkaListener(topics = "emprunt-livre", groupId = "lecteur-group-id")
    public void listen(ConsumerRecord<String, Lecteur> record) {
        Lecteur lecteur = record.value();
        // Vérifiez si le lecteur existe
        boolean lecteurExiste = lecteurService.existsById(lecteur.getId());
        if (lecteurExiste) {
            // Envoyez une réponse au microservice Emprunt
            Lecteur existingLecteur = lecteurService.findById(lecteur.getId());
            lecteurKafkaProducer.send("lecteur-exists", existingLecteur);
        }

    }


  
    
}

