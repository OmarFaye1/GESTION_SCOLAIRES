package uasz.sn.Gestion_Enseignement.Classes.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Eleve extends Utilisateur { // Héritage de Utilisateur


    private String adresse;
    private String password_clair;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_naissance;

    @ManyToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;

}
