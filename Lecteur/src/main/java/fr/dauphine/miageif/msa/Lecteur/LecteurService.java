package fr.dauphine.miageif.msa.Lecteur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class LecteurService {

    @Autowired
    private LecteurRepository lecteurRepository;

    public List<Lecteur> createLecteur(List<Lecteur> lecteurs) {
        return lecteurRepository.saveAll(lecteurs);
    }

    public Lecteur createLecteur(Lecteur lecteur) {
        return lecteurRepository.save(lecteur);
    }

    public Optional<Lecteur> getLecteurById(Long id) {
        return lecteurRepository.findById(id);
    }

    public List<Lecteur> getAllLecteurs() {
        return lecteurRepository.findAll();
    }
    
    public Lecteur findById(Long id) {
        return lecteurRepository.findById(id).orElse(null);
    }
    
    
    public Lecteur updateLecteur(Long id, Lecteur updatedLecteur) {
        return lecteurRepository.findById(id).map(lecteur -> {
            lecteur.setGenre(updatedLecteur.getGenre());
            lecteur.setNom(updatedLecteur.getNom());
            lecteur.setPrenom(updatedLecteur.getPrenom());
            lecteur.setDateNaissance(updatedLecteur.getDateNaissance());
            lecteur.setAdresse(updatedLecteur.getAdresse());
            return lecteurRepository.save(lecteur);
        }).orElseGet(() -> {
            updatedLecteur.setId(id);
            return lecteurRepository.save(updatedLecteur);
        });
    }

    public void deleteLecteurById(Long id) {
        lecteurRepository.deleteById(id);
    }

    public void setLastLivre(EmpruntDto empruntDto) {
        Long livreId = empruntDto.getLivreId();
        if(getLecteurById(empruntDto.getId()).isEmpty()) { return; }
        Lecteur lecteur = getLecteurById(empruntDto.getId()).get();
        lecteur.setLastLivre(livreId);
        updateLecteur(empruntDto.getLecteurId(), lecteur);
    }

}
