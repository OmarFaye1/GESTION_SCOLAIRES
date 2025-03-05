package uasz.sn.Gestion_Enseignement.Classes.modele;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eleveId;
    private String matiere;
    private double noteDevoir; // ✅ Ajout de la note du devoir
    private double noteComposition; // ✅ Ajout de la note de composition

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEleveId() { return eleveId; }
    public void setEleveId(Long eleveId) { this.eleveId = eleveId; }

    public String getMatiere() { return matiere; }
    public void setMatiere(String matiere) { this.matiere = matiere; }

    public double getNoteDevoir() { return noteDevoir; }
    public void setNoteDevoir(double noteDevoir) { this.noteDevoir = noteDevoir; }

    public double getNoteComposition() { return noteComposition; }
    public void setNoteComposition(double noteComposition) { this.noteComposition = noteComposition; }
}
