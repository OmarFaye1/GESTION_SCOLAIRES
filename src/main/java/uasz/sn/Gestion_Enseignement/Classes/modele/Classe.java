package uasz.sn.Gestion_Enseignement.Classes.modele;

import jakarta.persistence.*;

@Entity
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String libelle;
    private int effectif;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public int getEffectif() { return effectif; }
    public void setEffectif(int effectif) { this.effectif = effectif; }
}
