package uasz.sn.Gestion_Enseignement.Classes.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Classes.modele.Eleve;
import uasz.sn.Gestion_Enseignement.Classes.repository.EleveRepository;
import uasz.sn.Gestion_Enseignement.Classes.modele.Classe;

import java.util.List;

@Service
@Transactional
public class EleveService {

    @Autowired
    private EleveRepository eleveRepository;

    public void ajouterEleve(Eleve eleve) {
        eleveRepository.save(eleve);
    }

    public List<Eleve> afficherToutEleve() {
        return eleveRepository.findAll();
    }

    public Eleve afficherEleve(Long id) {
        return eleveRepository.findById(id).orElse(null);
    }

    public void modifierEleve(Eleve eleve) {
        Eleve eleveUpdate = eleveRepository.findById(eleve.getId()).orElse(null);
        if (eleveUpdate != null) {
            eleveUpdate.setCode(eleve.getCode());
            eleveUpdate.setNom(eleve.getNom());
            eleveUpdate.setPrenom(eleve.getPrenom());
            eleveUpdate.setAdresse(eleve.getAdresse());
            eleveRepository.save(eleveUpdate);
        }
    }

    public void supprimerEC(Eleve eleve) {
        eleveRepository.delete(eleve);
    }

    // Nouvelle méthode pour afficher les élèves par classe
    public List<Eleve> afficherElevesParClasse(Classe classe) {
        return eleveRepository.findByClasse(classe); // Supposons que vous avez cette méthode dans le repository
    }
}
