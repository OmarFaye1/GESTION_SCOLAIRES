package uasz.sn.Gestion_Enseignement.Classes.modele;

import jakarta.persistence.*;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "eleve_id", nullable = false)
    private Eleve eleve; // ✅ Utilisation d'une relation ManyToOne au lieu d'un Long

    @ManyToOne
    @JoinColumn(name = "matiere_id", nullable = false)
    private Matiere matiere; // ✅ Utilisation d'une relation ManyToOne au lieu d'un Long

    private double noteDevoir;
    private double noteComposition;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Eleve getEleve() { return eleve; }
    public void setEleve(Eleve eleve) { this.eleve = eleve; } // ✅ Mise à jour correcte

    public Matiere getMatiere() { return matiere; }
    public void setMatiere(Matiere matiere) { this.matiere = matiere; } // ✅ Mise à jour correcte

    public double getNoteDevoir() { return noteDevoir; }
    public void setNoteDevoir(double noteDevoir) { this.noteDevoir = noteDevoir; }

    public double getNoteComposition() { return noteComposition; }
    public void setNoteComposition(double noteComposition) { this.noteComposition = noteComposition; }
}
