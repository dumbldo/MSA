package fr.miage.dauphine.MSA.Emprunt;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import java.util.Optional;


@Slf4j
@Service
public class EmpruntService {

    @Autowired
    private EmpruntRepository empruntRepository;



    public void someMethod() {
        log.debug("Debug message");
        log.info("Info message");
        log.warn("Warn message");
        log.error("Error message");
    }


    public Emprunt create(Emprunt emprunt){
        return empruntRepository.save(emprunt);
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
