package fr.miage.dauphine.MSA.Emprunt;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmpruntRepository extends JpaRepository<Emprunt, Long>{
    List<Emprunt> findAll();
    <S extends Emprunt> S save(S emprunt);
    Optional<Emprunt> findOneById(Long id);
    void deleteById(long id);
    void deleteAllByIdInBatch(Iterable<Long> ids);
}
