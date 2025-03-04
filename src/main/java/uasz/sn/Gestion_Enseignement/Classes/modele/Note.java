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
    private double note; // ✅ S'assurer que c'est bien un double

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEleveId() { return eleveId; }
    public void setEleveId(Long eleveId) { this.eleveId = eleveId; }

    public String getMatiere() { return matiere; }
    public void setMatiere(String matiere) { this.matiere = matiere; }

    public double getNote() { return note; } // ✅ Vérifier que le type de retour est double
    public void setNote(double note) { this.note = note; } // ✅ Correction ici
}
