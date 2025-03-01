package uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele.Choix;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Repository.ChoixRepository;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Utilisateur.Modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Utilisateur.Repository.EnseignantRepository;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Modele.Classe;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Modele.Maquette;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChoixService {

    @Autowired
    private ChoixRepository choixRepository;

    public void save(Choix choix) {
        choixRepository.save(choix);
    }

    public List<Choix> rechercherTousLesChoix() {
        return choixRepository.findAll();  // Utilise findAll() pour récupérer tous les enregistrements de la table Choix
    }
    //rechercher choix par id
    public Choix rechercherParId(Long id) {
        Optional<Choix> choix = choixRepository.findById(id);
        return choix.orElse(null);  // Retourne null si le choix n'est pas trouvé
    }
    public List<Choix> rechercherParEnseignant(Long enseignantId) {
        // Récupérer tous les choix de l'enseignant via le repository
        return choixRepository.findByEnseignantId(enseignantId);
    }

    public boolean existsByEnseignantAndEnseignementAndClasseAndSemestre(Enseignant enseignant, Enseignement enseignement, Classe classe, String semestre) {
        return choixRepository.existsByEnseignantAndEnseignementAndClasseAndSemestre(enseignant, enseignement, classe, semestre);
    }
    //supprimer choix
    public void supprimerChoix(Long id) {
        choixRepository.deleteById(id);
    }

}

