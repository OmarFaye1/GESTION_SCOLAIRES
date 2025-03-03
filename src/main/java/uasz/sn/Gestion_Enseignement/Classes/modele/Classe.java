package uasz.sn.Gestion_Enseignement.Classes.modele;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le code de la classe est obligatoire")
    private String code;

    @NotBlank(message = "Le libellé de la classe est obligatoire")
    private String libelle;

    @NotNull(message = "L'effectif de la classe est obligatoire")
    @PositiveOrZero(message = "L'effectif doit être un nombre positif ou zéro")
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