package fr.miage.dauphine.MSA.Emprunt;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class EmpruntService {

    @Autowired
    private EmpruntRepository empruntRepository;
    private final Producer producer;

    public EmpruntService(Producer producer){
        this.producer = producer;
    }

    public void someMethod() {
        log.debug("Debug message");
        log.info("Info message");
        log.warn("Warn message");
        log.error("Error message");
    }


    public Emprunt create(Emprunt emprunt){
        try {
            producer.sendMessage(emprunt);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return empruntRepository.save(emprunt);
    }

    public void createAll(List<Emprunt> emprunts) {
        for (Emprunt emprunt : emprunts) {
            // logique de création d'emprunt pour chaque objet Emprunt dans la liste
            create(emprunt);
        }
    }

    public Optional<Emprunt> update(Long id, Emprunt newEmprunt){
        return empruntRepository.findOneById(id)
                .map(oldItem -> {
                    Emprunt updated = oldItem.updateWith(newEmprunt);
                    return empruntRepository.save(updated);
                });
    }

    public void deleteEmpruntById(Long id){
        empruntRepository.deleteById(id);
    }

    public void deleteEmpruntByIds(Iterable<Long> ids){
        empruntRepository.deleteAllByIdInBatch(ids);
    }

 
    



    

}
