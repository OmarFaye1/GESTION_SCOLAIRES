package uasz.sn.Gestion_Enseignement.Utilisateur.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Utilisateur.Modele.Enseignants;
import uasz.sn.Gestion_Enseignement.Utilisateur.Repository.EnseignantsRepository;
import java.util.List;

@Service
@Transactional
public class EnseignantsService {
    @Autowired
    private EnseignantsRepository enseignantsRepository;

    public Enseignants ajouter(Enseignants enseignants){
        return  enseignantsRepository.save(enseignants);
    }

    public List<Enseignants> Liste (){
        return enseignantsRepository.findAll();
    }

    public Enseignants rechercherParId(Long id) {
        System.out.println("Recherche de l'enseignant avec ID : " + id);
        Enseignants enseignants = enseignantsRepository.findById(id).orElse(null);
        System.out.println("Résultat de la recherche : " + (enseignants != null ? enseignants.toString() : "Aucun enseignant trouvé"));
        return enseignants;
    }


    public Enseignants modifier(Enseignants enseignants){
        return  enseignantsRepository.save(enseignants);
    }

    public void activer(Long id){
        Enseignants enseignants = enseignantsRepository.findById(id).get();
        if (enseignants.isActive()==true){
            enseignants.setActive(false);
        }else {
            enseignants.setActive(true);
        }
        enseignantsRepository.save(enseignants);
    }

    public void archiver(Long id){
        Enseignants enseignants = enseignantsRepository.findById(id).get();
        if (enseignants.isArchive()==true){
            enseignants.setArchive(false);
        }else {
            enseignants.setArchive(true);
        }
        enseignantsRepository.save(enseignants);
    }
}
