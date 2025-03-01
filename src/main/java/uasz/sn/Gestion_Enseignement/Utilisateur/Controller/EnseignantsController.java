package uasz.sn.Gestion_Enseignement.Utilisateur.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Utilisateur.Modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.Service.EnseignantService;

import java.security.Principal;
import java.util.List;

@Controller
public class EnseignantsController {

    private final UtilisateurService utilisateurService;
    private final EnseignantService enseignantService;

    @Autowired
    public EnseignantsController(UtilisateurService utilisateurService,
                                 EnseignantService enseignantService) {
        this.utilisateurService = utilisateurService;
        this.enseignantService = enseignantService;
    }

    @GetMapping("/ChefDepartement/Enseignant")
    public String chefDepartementEnseignant(Model model, Principal principal) {
        // Récupérer l'utilisateur connecté
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));

        // Récupérer la liste des enseignants
        List<Enseignant> enseignants = enseignantService.liste(); // Utilisez liste() au lieu de Liste()
        model.addAttribute("enseignants", enseignants);

        return "chefDepartement_Enseignant";
    }
}