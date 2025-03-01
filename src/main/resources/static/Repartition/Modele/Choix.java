package uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Utilisateur.Modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Modele.Classe;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Choix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "enseignement_id")
    private Enseignement enseignement;

    @Column(name = "date_choix")
    private LocalDate dateChoix;  // Date du choix

    //l'attribut semestre permet de trier les elements a l'affichage pour savoir si c'est au 1er ou 2nd semestre
    private String semestre ;

    //une classe peut avoir plusieurs maquettes
    @ManyToOne
    private Classe classe;
    private Boolean validated = false; // Champ pour savoir si c'est sélectionné

}
