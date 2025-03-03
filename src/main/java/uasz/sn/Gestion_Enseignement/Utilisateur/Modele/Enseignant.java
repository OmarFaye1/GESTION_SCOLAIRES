package uasz.sn.Gestion_Enseignement.Utilisateur.Modele;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Enseignant extends Enseignants {
    private String matricule;
    private String email;
    private boolean active = true; // Par défaut, un enseignant est actif
    private boolean archive = false; // Par défaut, un enseignant n'est pas archivé
}