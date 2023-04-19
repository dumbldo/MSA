package fr.dauphine.miageif.msa.Lecteur;

import java.time.LocalDate;
import lombok.Data;

@Data
public class EmpruntDto {
    private Long id;

    private Long livreId;

    private Long lecteurId;

    private LocalDate dateEmprunt;

    private LocalDate dateRetour;
}
