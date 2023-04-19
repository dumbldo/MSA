package fr.dauphine.miageif.lecteur;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LecteurRepository extends JpaRepository<Lecteur, Long> {


    <S extends Lecteur> S save(S lecteur);

    Optional<Lecteur> findById(Long id);

    List<Lecteur> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

   
    


}
