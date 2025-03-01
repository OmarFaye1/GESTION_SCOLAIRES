package uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele.Choix;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele.Repartition;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Repository.ChoixRepository;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Repository.EnseignementRepository;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Repository.RepartitionRepository;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Utilisateur.Modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Utilisateur.Repository.EnseignantRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class RepartitionService {
    @Autowired
    private ChoixRepository choixRepository;

    @Autowired
    private RepartitionRepository repartitionRepository;

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private EnseignementRepository enseignementRepository;

    // la recuperation de tous les choix
    public List<Choix> getChoixTous(){
        return choixRepository.findAll();
    }

    public Choix getChoixById(Long choixId) {
        return choixRepository.findById(choixId)
                .orElseThrow(() -> new RuntimeException("Choix introuvable"));
    }
    @Transactional
    public void validerChoix(Choix choix) {
        // Créer une nouvelle répartition pour l'enseignant et l'enseignement
        Repartition repartition = new Repartition();
        repartition.setEnseignant(choix.getEnseignant());
        repartition.setEnseignement(choix.getEnseignement());
        repartition.setDateValidation(LocalDate.now());
        repartition.setClasse(choix.getClasse());
        repartition.setSemestre(choix.getSemestre());

        // Sauvegarder la répartition dans la base de données
        repartitionRepository.save(repartition);
        // Marquer le choix comme validé
        choix.setValidated(true);
        // Sauvegarder la répartition dans la base de données avant la suppression
        repartitionRepository.save(repartition);
        // Supprimer les choix non validés pour le même enseignement
        choixRepository.deleteNonValidesByEnseignementId(choix.getEnseignement().getId());
    }
    public void supprimerChoix(Long choixId) {
        choixRepository.deleteById(choixId);
    }
    // Récupérer toutes les répartitions
    public List<Repartition> getAllRepartitions() {
        return repartitionRepository.findAll();
    }

    public List<Map<String, Object>> getRepartitionByType() {
        List<Repartition> repartitions = repartitionRepository.findAll();
        Map<String, Map<String, Object>> resultMap = new LinkedHashMap<>();

        for (Repartition repartition : repartitions) {
            String enseignementKey = repartition.getEnseignement().getLibelle() + "_" + repartition.getClasse().getNiveau();

            if (!resultMap.containsKey(enseignementKey)) {
                Map<String, Object> data = new HashMap<>();
                data.put("classe", repartition.getClasse().getNiveau());
                data.put("semestre", repartition.getSemestre());
                data.put("uniteEnseignement", repartition.getEnseignement().getLibelle());
                data.put("credit", repartition.getEnseignement().getEc().getUe().getCredit());
                data.put("enseignantCM", "");
                data.put("responsableTD", "");
                data.put("responsableTP", "");
                data.put("td", repartition.getEnseignement().getEc().getTd()); // Ajout du TD
                data.put("tp", repartition.getEnseignement().getEc().getTp()); // Ajout du TP

                resultMap.put(enseignementKey, data);
            }

            String type = String.valueOf(repartition.getEnseignement().getTypeEnseignement());
            String enseignantNom = repartition.getEnseignant().getNom() + " " + repartition.getEnseignant().getPrenom();

            // Placer l'enseignant dans la bonne colonne
            switch (type) {
                case "CM":
                    resultMap.get(enseignementKey).put("enseignantCM", enseignantNom);
                    break;
                case "TD":
                    resultMap.get(enseignementKey).put("responsableTD", enseignantNom);
                    break;
                case "TP":
                    resultMap.get(enseignementKey).put("responsableTP", enseignantNom);
                    break;
            }
        }

        return new ArrayList<>(resultMap.values());
    }





    public List<Map<String, Object>> getRepartitionByClasse(Long classeId) {
        List<Repartition> repartitions = repartitionRepository.findByClasseId(classeId);
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (Repartition repartition : repartitions) {
            Map<String, Object> data = new HashMap<>();
            data.put("classe", repartition.getClasse().getNiveau());
            data.put("semestre", repartition.getSemestre());
            data.put("uniteEnseignement", repartition.getEnseignement().getLibelle());
            data.put("credit", repartition.getEnseignement().getEc().getUe().getCredit());
            data.put("enseignantCM", "");
            data.put("responsableTD", "");
            data.put("responsableTP", "");
            data.put("td", repartition.getEnseignement().getEc().getTd());
            data.put("tp", repartition.getEnseignement().getEc().getTp());

            String type = String.valueOf(repartition.getEnseignement().getTypeEnseignement());
            String enseignantNom = repartition.getEnseignant().getNom() + " " + repartition.getEnseignant().getPrenom();

            switch (type) {
                case "CM":
                    data.put("enseignantCM", enseignantNom);
                    break;
                case "TD":
                    data.put("responsableTD", enseignantNom);
                    break;
                case "TP":
                    data.put("responsableTP", enseignantNom);
                    break;
            }

            resultList.add(data);
        }

        return resultList;
    }


}
