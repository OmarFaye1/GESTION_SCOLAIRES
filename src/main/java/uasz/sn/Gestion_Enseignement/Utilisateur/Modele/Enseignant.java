package uasz.sn.Gestion_Enseignement.Utilisateur.Modele;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.Gestion_Enseignement.Classes.modele.Matiere;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Enseignant extends Enseignants {

    private String matricule;
    private String email;
    private String password_clair;


    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matiere> matieres; // ✅ Liste des matières enseignées

    public Enseignant(String matricule, String email, boolean active, Boolean archive) {
        this.matricule = matricule;
        this.email = email;
    }
}
