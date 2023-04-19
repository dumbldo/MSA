package fr.dauphine.miageif.msa.Lecteur;


import javax.persistence.*;



@Entity
public class Lecteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String genre;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String adresse;
    private Long lastLivre;

    public Lecteur() {
    }

    public Lecteur(String genre, String nom, String prenom, String dateNaissance, String adresse, Long lastLivre) {
        this.genre = genre;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.lastLivre = lastLivre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    public Long getLastLivre() {
        return lastLivre;
    }

    public void setLastLivre(Long lastLivre) {
        this.lastLivre = lastLivre;
    }
}
