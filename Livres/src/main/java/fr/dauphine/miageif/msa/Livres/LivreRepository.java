package fr.dauphine.miageif.msa.Livres;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LivreRepository extends JpaRepository<Livre, Long> {
    // lister
    List<Livre> findAll();

    // creer ou modifier
    <S extends Livre> S save(S livre);

    // recuperer un livre
    Optional<Livre> findById(Long id);

    // supprimer
    void deleteById(long id);

    // supprimer un liste de livres
    void deleteAllByIdInBatch(Iterable<Long> ids);
}
