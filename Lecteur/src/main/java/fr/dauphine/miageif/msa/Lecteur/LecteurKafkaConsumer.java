package fr.dauphine.miageif.msa.Lecteur;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dauphine.miageif.msa.Lecteur.LecteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static org.apache.kafka.common.requests.DeleteAclsResponse.log;

@Service
public class LecteurKafkaConsumer {

    private static final String orderTopic = "${topic.name}";

    private final ObjectMapper objectMapper;
    private final LecteurService lecteurService;

    @Autowired
    public LecteurKafkaConsumer(ObjectMapper objectMapper, LecteurService lecteurService) {
        this.objectMapper = objectMapper;
        this.lecteurService = lecteurService;
    }

    @KafkaListener(topics = orderTopic)
    public void consumeMessage(String message) throws JsonProcessingException {
        log.info("message consumed {}", message);
        EmpruntDto empruntDto = objectMapper.readValue(message, EmpruntDto.class);
        lecteurService.setLastLivre(empruntDto);
    }
}

