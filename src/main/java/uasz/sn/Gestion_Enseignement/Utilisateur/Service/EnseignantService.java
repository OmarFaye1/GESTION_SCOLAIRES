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
        Optional<Enseignant> optionalEnseignant = enseignantRepository.findByMatricule(enseignant.getMatricule());
        if (optionalEnseignant.isPresent()) {
            throw new ResourceAlreadyExistException("Le matricule " + enseignant.getMatricule() + " existe déjà");
        }
        return enseignantRepository.save(enseignant);
    }

    public List<Enseignant> liste() { // Méthode en minuscules
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
}