package uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele.TypeEnseignement;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Repository.EnseignementRepository;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Modele.Classe;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Modele.EC;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Modele.Maquette;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Repository.ECRepository;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Service.ClasseService;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Service.ECService;

import java.util.List;

@Service
public class EnseignementService {

    @Autowired
    private EnseignementRepository enseignementRepository;

    @Autowired
    private ClasseService classeService;

    @Autowired
    private ECService ecService;
    @Autowired
    private ECRepository ecRepository;

    // Appel de la méthode au démarrage de l'application
    @PostConstruct
    public void init() {
        // Récupérer tous les EC pour lesquels on veut créer des enseignements
        List<EC> ecList = ecRepository.findAll();
        for (EC ec : ecList) {
            creerEnseignementsPourEC(ec.getId());
        }
    }

    // Méthode pour créer des enseignements pour chaque EC avec CM, TD, TP
    public void creerEnseignementsPourEC(Long ecId) {
        EC ec = ecRepository.findById(ecId).orElseThrow(() -> new RuntimeException("EC non trouvé"));

        // Vérifier et insérer uniquement si l'enseignement n'existe pas déjà
        if (!enseignementRepository.existsByEcAndTypeEnseignement(ec, TypeEnseignement.CM)) {
            Enseignement enseignementCM = new Enseignement();
            enseignementCM.setEc(ec);
            enseignementCM.setTypeEnseignement(TypeEnseignement.CM);
            enseignementRepository.save(enseignementCM);
        }

        if (!enseignementRepository.existsByEcAndTypeEnseignement(ec, TypeEnseignement.TD)) {
            Enseignement enseignementTD = new Enseignement();
            enseignementTD.setEc(ec);
            enseignementTD.setTypeEnseignement(TypeEnseignement.TD);
            enseignementRepository.save(enseignementTD);
        }

        if (!enseignementRepository.existsByEcAndTypeEnseignement(ec, TypeEnseignement.TP)) {
            Enseignement enseignementTP = new Enseignement();
            enseignementTP.setEc(ec);
            enseignementTP.setTypeEnseignement(TypeEnseignement.TP);
            enseignementRepository.save(enseignementTP);
        }
    }

    public Enseignement findByEcAndTypeEnseignement(EC ec, TypeEnseignement typeEnseignement) {
        return enseignementRepository.findByEcAndTypeEnseignement(ec, typeEnseignement);
    }

    public void save(Enseignement enseignement) {
        enseignementRepository.save(enseignement);
    }

    public Enseignement rechercherParId(Long enseignementId) {
        // Utilisation du repository pour récupérer l'enseignement par son ID
        return enseignementRepository.findById(enseignementId).orElse(null);
    }

    //remp_l_i_r_ le_s_ a_u_t_r_e_s_ v_ale_u_r d_e l'_e_c e_n ca_s_ 'd'_a_jo_ut
    public void mettreAJourEnseignement(Long ecId, TypeEnseignement typeEnseignement, Long classeId) {
        // Récupérer l'EC en utilisant son ID
        EC ec = ecService.afficherEC(ecId);
        if (ec != null) {
            // Trouver l'enseignement en utilisant l'objet EC et le type d'enseignement
            Enseignement enseignement = enseignementRepository.findByEcAndTypeEnseignement(ec, typeEnseignement);
            if (enseignement != null) {
                // Remplir les autres valeurs nécessaires (par exemple, classe et libelle)
                enseignement.setClasse(classeService.afficherClasse(classeId));
                enseignement.setLibelle(ec.getLibelle());
                enseignement.setSelected(true); // Marquer comme sélectionné si c'est le cas
                enseignementRepository.save(enseignement);
                System.out.println("Enseignement mis à jour : " + enseignement.getLibelle());
            } else {
                System.out.println("Enseignement non trouvé pour EC ID " + ecId + " et type " + typeEnseignement);
            }
        } else {
            System.out.println("EC non trouvé pour ID : " + ecId);
        }
    }

}
