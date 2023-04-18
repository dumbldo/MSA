package fr.miage.dauphine.MSA.Emprunt;


import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Emprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long livreId;

    private Long lecteurId;

    private LocalDate dateEmprunt;

    private LocalDate dateRetour;

    public Emprunt() {

    }

    public Emprunt(Long id, Long livreId, Long lecteurId, LocalDate dateEmprunt, LocalDate dateRetour) {
        super();
        this.id = id;
        this.livreId = livreId;
        this.lecteurId = lecteurId;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    public Long getId() {
        return id;
    }

    public Long getLivreId() {
        return livreId;
    }

    public Long getLecteurId() {
        return lecteurId;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public Emprunt updateWith(Emprunt item) {
        return new Emprunt(
                this.id,
                item.livreId,
                item.lecteurId,
                item.dateEmprunt,
                item.dateRetour
        );
    }

}
