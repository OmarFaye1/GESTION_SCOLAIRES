package uasz.sn.Gestion_Enseignement.Utilisateur.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Utilisateur.Modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.Repository.EnseignantRepository;
import uasz.sn.Gestion_Enseignement.exception.ResourceAlreadyExistException;
import uasz.sn.Gestion_Enseignement.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnseignantService {
    @Autowired
    private EnseignantRepository enseignantRepository;

    public Enseignant ajouter(Enseignant enseignant) {
        // Vérifier si l'enseignant existe déjà
        Optional<Enseignant> optionalEnseignant = enseignantRepository.findByMatricule(enseignant.getMatricule());
        if (optionalEnseignant.isPresent()) {
            throw new ResourceAlreadyExistException("Le matricule " + enseignant.getMatricule() + " existe déjà");
        }

        // Si archive n'est pas initialisé, définir une valeur par défaut
        if (enseignant.getArchive() == null) {
            enseignant.setArchive(false); // Définir par défaut sur false
        }

        // Enregistrer l'enseignant dans la base de données
        return enseignantRepository.save(enseignant);
    }


    public List<Enseignant> liste() {
        return enseignantRepository.findAll();
    }

    public Enseignant rechercher(Long id) {
        return enseignantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant avec ID " + id + " n'existe pas"));
    }

    public Enseignant modifier(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }

    public void activer(Long id) {
        Enseignant enseignant = rechercher(id);
        enseignant.setActive(true);
        enseignantRepository.save(enseignant);
    }

    public void archiver(Long id) {
        Enseignant enseignant = rechercher(id);
        enseignant.setArchive(true);
        enseignantRepository.save(enseignant);
    }

    public Enseignant rechercherParUsername(String username) {
        return enseignantRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant avec email " + username + " n'existe pas"));
    }

    public void supprimer(Long id) {
        Enseignant enseignant = rechercher(id);
        enseignantRepository.delete(enseignant);
    }

    public Enseignant rechercherParMatricule(String matricule) {
        return enseignantRepository.findByMatricule(matricule)
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant avec matricule " + matricule + " n'existe pas"));
    }

    public List<Enseignant> listerEnseignantsActifs() {
        return enseignantRepository.findByActiveTrue();
    }

    public List<Enseignant> listerEnseignantsArchives() {
        return enseignantRepository.findByArchiveTrue();
    }

    public void desactiver(Long id) {
        Enseignant enseignant = rechercher(id);
        enseignant.setActive(false);
        enseignantRepository.save(enseignant);
    }

    public void desarchiver(Long id) {
        Enseignant enseignant = rechercher(id);
        enseignant.setArchive(false);
        enseignantRepository.save(enseignant);
    }
}