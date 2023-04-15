package fr.dauphine.miageif.msa.Livres;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn;
    private String auteur;
    private String titre;
    private String editeur;
    private int edition;
    private boolean empruntId;

    public Livre() {
    }

    public Livre(String isbn, String auteur, String titre, String editeur, int edition) {
        this.isbn = isbn;
        this.auteur = auteur;
        this.titre = titre;
        this.editeur = editeur;
        this.edition = edition;
        this.empruntId = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public boolean isEmprunted(){ return empruntId; }
    public void setEmpruntId(boolean empruntId){ this.empruntId = empruntId; }
}
