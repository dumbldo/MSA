package fr.dauphine.miageif.msa.Livres;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LivreService {

    @Autowired
    private LivreRepository livreRepository;

    public Livre createLivre(Livre livre) {
        return livreRepository.save(livre);
    }

    public Optional<Livre> getLivreById(Long id) {
        return livreRepository.findById(id);
    }

    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }

    public Livre updateLivre(Long id, Livre updatedLivre) {
        return livreRepository.findById(id).map(livre -> {
            livre.setIsbn(updatedLivre.getIsbn());
            livre.setAuteur(updatedLivre.getAuteur());
            livre.setTitre(updatedLivre.getTitre());
            livre.setEditeur(updatedLivre.getEditeur());
            livre.setEdition(updatedLivre.getEdition());
            livre.setEmpruntId(updatedLivre.getEmpruntId());;
            return livreRepository.save(livre);
        }).orElseGet(() -> {
            return null;
        });
    }

    public void updateEmpruntId(EmpruntDto empruntDto) {
        Livre livre = livreRepository.findById(empruntDto.getLivreId()).get();
        livre.setEmpruntId(empruntDto.getId());
        updateLivre(empruntDto.getLivreId(), livre);
    }

    public void deleteLivreById(Long id) {
        livreRepository.deleteById(id);
    }
}
