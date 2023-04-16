package fr.dauphine.miageif.msa.Livres;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivreRepository extends JpaRepository<Livre, Long> {
    // lister
    List<Livre> findAll();

    // creer ou modifier
    <S extends Livre> S save(S livre);

    // recuperer un livre
    Optional<Livre> findById(Long id);

    // supprimer
    void deleteById(Long id);

    // supprimer un liste de livres
    void deleteAllByIdInBatch(Iterable<Long> ids);
}
