package uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.EmploisDuTemps.Modele.EmploisDuTemps;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Utilisateur.Modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Modele.Classe;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repartition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "enseignant_id", nullable = false)
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "enseignement_id", nullable = false)
    private Enseignement enseignement;

    private LocalDate dateValidation;

    @ManyToOne
    private Classe classe;

    private String semestre ;

    @OneToMany(mappedBy = "repartition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmploisDuTemps> emploisDuTemps; // Une répartition peut avoir plusieurs emplois du temps (différents jours/heures)

}
