package uasz.sn.Gestion_Enseignement.Classes.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Classes.modele.Classe;
import uasz.sn.Gestion_Enseignement.Classes.modele.Eleve;
import uasz.sn.Gestion_Enseignement.Classes.repository.ClasseRepository;
import uasz.sn.Gestion_Enseignement.exception.ResourceNotFoundException;

import java.util.List;

@Service
@Transactional
public class ClasseService {
    @Autowired
    private ClasseRepository classeRepository;

    // Implémenter la méthode getAllClasses
    public List<Classe> getAllClasses() {
        return classeRepository.findAll(); // Utiliser le repository pour récupérer toutes les classes
    }

    public void ajouterClasse(Classe classe) {
        // Vérifier si une classe avec le même code ou libellé existe déjà
        if (classeRepository.existsByCodeOrLibelle(classe.getCode(), classe.getLibelle())) {
            throw new IllegalArgumentException("Une classe avec ce code ou libellé existe déjà !");
        }
        classeRepository.save(classe);
    }

    public List<Classe> afficherToutClasse() {
        return classeRepository.findAll();
    }

    public Classe afficherClasse(Long id) {
        return classeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classe avec ID " + id + " n'existe pas"));
    }

    public void modifierClasse(Classe classe) {
        Classe classeUpdate = classeRepository.findById(classe.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Classe avec ID " + classe.getId() + " n'existe pas"));
        classeUpdate.setCode(classe.getCode());
        classeUpdate.setLibelle(classe.getLibelle());
        classeUpdate.setEffectif(classe.getEffectif());
        classeRepository.save(classeUpdate);
    }

    public void supprimerClasse(Long id) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classe avec ID " + id + " n'existe pas"));

        // Supprimer les élèves associés à cette classe
        for (Eleve eleve : classe.getEleves()) {
            eleve.setClasse(null); // Détacher les élèves de la classe
        }

        classeRepository.delete(classe);
    }



    public List<Eleve> afficherLesEleve(Classe classe) {
        return classeRepository.findByClasse(classe);
    }
    public Classe trouverClasseParCode(String code) {
        return classeRepository.findByCode(code).orElse(null);
    }



}