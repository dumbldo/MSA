package fr.dauphine.miageif.msa.Lecteur;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LecteurRepository extends JpaRepository<Lecteur, Long> {

    List<Lecteur> saveAll(Iterable<Lecteur> lecteurs);

    <S extends Lecteur> save(S lecteur);

    Optional<Lecteur> findById(Long id);

    List<Lecteur> findAll();

    void deleteById(Long id);




}
