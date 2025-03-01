package uasz.sn.Gestion_Enseignement.Utilisateur.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Utilisateur.Modele.Eleves;
import uasz.sn.Gestion_Enseignement.Utilisateur.Repository.ElevesRepository;


import java.util.List;

@Service
@Transactional
public class ElevesService {
    @Autowired
    private ElevesRepository elevesRepository;

    public Eleves ajouter(Eleves eleves){
        return  elevesRepository.save(eleves);
    }

    public List<Eleves> Liste (){
        return elevesRepository.findAll();
    }

    public Eleves rechercher(Long id){
        return elevesRepository.findById(id).get();
    }

    public Eleves modifier(Eleves eleves){
        return  elevesRepository.save(eleves);
    }

    public void supprimer(Long id){
        elevesRepository.deleteById(id);
    }
}

