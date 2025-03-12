package uasz.sn.Gestion_Enseignement.Utilisateur.Modele;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Enseignant extends Enseignants {

    private String matricule;
    private String email;
    private String password_clair;
    private boolean active = true; // Par défaut, un enseignant est actif

    @Column(name = "archive", nullable = false)
    private Boolean archive = false;  // Utilisation de Boolean pour permettre le null

    // Constructeur modifié pour permettre l'initialisation de archive avec une valeur par défaut
    public Enseignant(String matricule, String email, boolean active, Boolean archive) {
        this.matricule = matricule;
        this.email = email;
        this.active = active;
        this.archive = (archive != null) ? archive : false;  // Si 'archive' est null, définir à false
    }

    // Getter pour l'attribut 'archive'
    public Boolean getArchive() {
        return archive;
    }

    // Setter pour l'attribut 'archive'
    public void setArchive(Boolean archive) {
        this.archive = archive;
    }
}
