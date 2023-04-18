package fr.dauphine.miageif.msa.Livres;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    private static final String orderTopic = "${topic.name}";

    private final ObjectMapper objectMapper;
    private final LivreService livreService;

    @Autowired
    public Consumer(ObjectMapper objectMapper, LivreService livreService) {
        this.objectMapper = objectMapper;
        this.livreService = livreService;
    }

    @KafkaListener(topics = orderTopic)
    public void consumeMessage(String message) throws JsonProcessingException {
        log.info("message consumed {}", message);
        EmpruntDto empruntDto = objectMapper.readValue(message, EmpruntDto.class);
        livreService.updateEmpruntId(empruntDto);
    }

}