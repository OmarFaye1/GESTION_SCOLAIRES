package uasz.sn.Gestion_Enseignement.Utilisateur.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Classes.modele.Matiere;
import uasz.sn.Gestion_Enseignement.Utilisateur.Modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.Repository.EnseignantRepository;
import uasz.sn.Gestion_Enseignement.exception.ResourceAlreadyExistException;
import uasz.sn.Gestion_Enseignement.exception.ResourceNotFoundException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EnseignantService {
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public String generateMatricule() {
        // Récupère l'année en cours
        String year = new SimpleDateFormat("yyyy").format(new Date());

        // Générer un matricule unique (ici, par exemple, on utilise un compteur basé sur la base de données)
        long count = enseignantRepository.count() + 1; // Compte les enseignants et ajoute 1 pour le prochain
        return "ENS-" + year + "-" + String.format("%04d", count); // Génère un matricule comme : ENS-2025-0001
    }
    // Ajouter un enseignant
    public Enseignant ajouter(Enseignant enseignant) {
        // Générer le matricule avant d'enregistrer
        enseignant.setMatricule(generateMatricule());
        Optional<Enseignant> optionalEnseignant = enseignantRepository.findByMatricule(enseignant.getMatricule());
        if (optionalEnseignant.isPresent()) {
            throw new ResourceAlreadyExistException("Le matricule " + enseignant.getMatricule() + " existe déjà");
        }
        return enseignantRepository.save(enseignant);
    }

    // Vérifier si un username existe
    public boolean usernameExists(String username) {
        return enseignantRepository.existsByEmail(username);
    }



    // Liste des enseignants
    public List<Enseignant> liste() {
        return enseignantRepository.findAll();
    }

    // Recherche un enseignant par son ID
    public Enseignant rechercher(Long id) {
        return enseignantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant avec ID " + id + " n'existe pas"));
    }

    public void modifierEnseignant(Enseignant enseignant) {
        System.out.println("🔄 Modification en base de données pour ID : " + enseignant.getId());

        Enseignant enseignantUpdate = enseignantRepository.findById(enseignant.getId()).orElse(null);

        if (enseignantUpdate == null) {
            System.out.println("⚠️ Enseignant non trouvé en base !");
            return;
        }

        // Mise à jour des attributs
        enseignantUpdate.setMatricule(enseignant.getMatricule());
        enseignantUpdate.setNom(enseignant.getNom());
        enseignantUpdate.setPrenom(enseignant.getPrenom());
        enseignantUpdate.setUsername(enseignant.getUsername());

        if (enseignant.getPassword() != null && !enseignant.getPassword().isEmpty()) {
            enseignantUpdate.setPassword(passwordEncoder.encode(enseignant.getPassword()));
        }

        enseignantRepository.save(enseignantUpdate);
        System.out.println("✅ Enregistrement terminé !");
    }




    // Recherche un enseignant par matricule
    public Enseignant rechercherParMatricule(String matricule) {
        return enseignantRepository.findByMatricule(matricule)
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant avec matricule " + matricule + " n'existe pas"));
    }
    public void supprimer(Long id) {
        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant avec ID " + id + " n'existe pas"));

        enseignantRepository.delete(enseignant);
    }
    // Liste des enseignants archivés
    public List<Enseignant> listerEnseignantsArchives() {
        return enseignantRepository.findByArchiveTrue();
    }
    // Vérifier si le username existe déjà

    // Récupérer les matières d'un enseignant par son ID
    public List<Matiere> getMatieresByEnseignant(Long enseignantId) {
        Enseignant enseignant = enseignantRepository.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));
        return enseignant.getMatieres(); // Retourne la liste des matières de cet enseignant
    }

}
