package uasz.sn.Gestion_Enseignement.Classes.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Eleve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String adresse;
    private String code;

    @Column(unique = true, nullable = true)  // Assure que l'email est unique
    private String username;

    private String password;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Assure un bon format pour les formulaires Thymeleaf
    private Date date_naissance;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @ManyToOne
    @JoinColumn(name = "classe_id") // Crée une clé étrangère pour lier l'élève à sa classe
    private Classe classe;



}
