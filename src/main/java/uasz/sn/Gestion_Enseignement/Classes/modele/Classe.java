package uasz.sn.Gestion_Enseignement.Classes.modele;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le code de la classe est obligatoire")
    private String code;

    @NotBlank(message = "Le libellé de la classe est obligatoire")
    private String libelle;

    @NotNull(message = "L'effectif de la classe est obligatoire")
    @PositiveOrZero(message = "L'effectif doit être un nombre positif ou zéro")
    private int effectif;

    @OneToMany(mappedBy = "classe")
    private List<Eleve> eleves; // Liste des élèves dans la class

    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matiere> matieres; // ✅ Liste des matières enseignées


}