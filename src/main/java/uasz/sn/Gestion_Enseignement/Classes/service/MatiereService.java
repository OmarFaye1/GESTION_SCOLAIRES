package uasz.sn.Gestion_Enseignement.Classes.service;

import uasz.sn.Gestion_Enseignement.Classes.modele.Matiere;
import uasz.sn.Gestion_Enseignement.Classes.repository.MatiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatiereService {

    @Autowired
    private MatiereRepository matiereRepository;

    // Ajouter une matière
    public Matiere ajouterMatiere(Matiere matiere) {
        return matiereRepository.save(matiere);
    }

    // Modifier une matière
    public Matiere modifierMatiere(Matiere matiere) {
        return matiereRepository.save(matiere);
    }

    // Supprimer une matière
    public void supprimerMatiere(Long id) {
        matiereRepository.deleteById(id);
    }

    // Lister toutes les matières
    public List<Matiere> listerToutesMatieres() {
        return matiereRepository.findAll();
    }

    // Trouver une matière par son ID
    public Matiere trouverMatiereParId(Long id) {
        return matiereRepository.findById(id).orElse(null);
    }

    // Trouver une matière par son code
    public Matiere trouverMatiereParCode(String code) {
        return matiereRepository.findByCode(code);
    }

    // Trouver une matière par son nom
    public Matiere trouverMatiereParNom(String nom) {
        return matiereRepository.findByNom(nom);
    }
}