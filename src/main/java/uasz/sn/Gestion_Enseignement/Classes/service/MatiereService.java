package uasz.sn.Gestion_Enseignement.Classes.service;

import uasz.sn.Gestion_Enseignement.Classes.modele.Matiere;
import uasz.sn.Gestion_Enseignement.Classes.repository.MatiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MatiereService {

    @Autowired
    private MatiereRepository matiereRepository;

    public List<Matiere> getMatieresByClasse(Long classeId) {
        return matiereRepository.findByClasseId(classeId);
    }

    public List<Matiere> getMatieresByEnseignantId(Long enseignantId) {
        return matiereRepository.findByEnseignantId(enseignantId);
    }

    // ✅ Récupérer toutes les matières
    public List<Matiere> getAllMatieres() {
        return matiereRepository.findAll();
    }


    public void ajouterMatiere(Matiere matiere) {
        matiereRepository.save(matiere);
    }

    // ✅ Méthode pour récupérer une matière par ID
    public Matiere getMatiereById(Long id) {
        Optional<Matiere> matiere = matiereRepository.findById(id);
        return matiere.orElseThrow(() -> new RuntimeException("Matière introuvable avec l'ID : " + id));
    }


}
