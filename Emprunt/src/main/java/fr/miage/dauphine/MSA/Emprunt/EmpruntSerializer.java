package fr.miage.dauphine.MSA.Emprunt;

import org.apache.kafka.common.serialization.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.Map;

public class EmpruntSerializer implements Serializer<Emprunt> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // nothing to configure
    }

    @Override
    public byte[] serialize(String topic, Emprunt data) {
        if (data == null)
            return null;

        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing Emprunt object", e);
        }
    }

    @Override
    public void close() {
        // nothing to close
    }
}

