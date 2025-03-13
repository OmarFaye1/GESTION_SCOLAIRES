package uasz.sn.Gestion_Enseignement.Classes.modele;

import jakarta.persistence.*;
import uasz.sn.Gestion_Enseignement.Utilisateur.Modele.Enseignant;

import java.util.List;

@Entity
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "enseignant_id", nullable = false) // ✅ Relation avec Enseignant
    private Enseignant enseignant;

    // 🚀 Les relations Matière - Classe et Matière - Note ne sont PAS modifiées
    @ManyToOne
    @JoinColumn(name = "classe_id", nullable = false)
    private Classe classe;

    @OneToMany(mappedBy = "matiere", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;

    public Matiere() {}

    public Matiere(String nom, Enseignant enseignant, Classe classe) {
        this.nom = nom;
        this.enseignant = enseignant;
        this.classe = classe;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Enseignant getEnseignant() { return enseignant; }
    public void setEnseignant(Enseignant enseignant) { this.enseignant = enseignant; }

    public Classe getClasse() { return classe; }
    public void setClasse(Classe classe) { this.classe = classe; }
}
