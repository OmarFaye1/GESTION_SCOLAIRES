package uasz.sn.Gestion_Enseignement.Utilisateur.Modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "id")
public abstract class Enseignants extends Utilisateur {
    private String Specialite;
    private boolean archive;


}
