package uasz.sn.Gestion_Enseignement.Utilisateur.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Role;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Utilisateur.Modele.Eleves;
import uasz.sn.Gestion_Enseignement.Utilisateur.Service.EnseignantsService;
import uasz.sn.Gestion_Enseignement.Utilisateur.Service.EnseignantService;
import uasz.sn.Gestion_Enseignement.Utilisateur.Service.ElevesService;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ElevesController {
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private EnseignantsService enseignantsService;

    @Autowired
    private EnseignantService enseignantService;
    @Autowired
    private ElevesService elevesService;

    private PasswordEncoder passwordEncoder;

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @RequestMapping(value = "/Eleve/Accueil", method = RequestMethod.GET)
    public String accueil_Vacataire(Model model, Principal principal){
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));
        return "template_Eleve";
    }

    @RequestMapping (value = "/ChefDepartement/ajouterEleve", method = RequestMethod.POST)
    public String ajouter_Eleve(Eleves eleves){
        String password = passwordEncoder().encode("Passer123");
        eleves.setPassword(password); eleves.setDateCreation(new Date());
        Role role = utilisateurService.ajouter_Role(new Role("Eleve"));
        enseignantsService.ajouter(eleves);
        List<Role> roles = new ArrayList<>(); roles.add(role); eleves.setRoles(roles);
        enseignantsService.ajouter(eleves);

        return "redirect:/ChefDepartement/Enseignant";
    }

    @RequestMapping (value = "/ChefDepartement/modifierEleve", method = RequestMethod.POST)
    public String modifier_Eleve(Eleves eleves){
        Eleves eleves_modif = elevesService.rechercher((eleves).getId());
        eleves_modif.setNom(eleves.getNom());
        eleves_modif.setPrenom(eleves.getPrenom());
        eleves_modif.setSpecialite(eleves.getSpecialite());
        eleves_modif.setNiveau(eleves.getNiveau());
        enseignantsService.modifier(eleves_modif);
        return "redirect:/ChefDepartement/Enseignant";
    }

    @RequestMapping (value = "/ChefDepartement/activerVacataire", method = RequestMethod.POST)
    public String activer_Eleve(Long id){
        enseignantsService.activer(id);
        return "redirect:/ChefDepartement/Enseignant";
    }

    @RequestMapping (value = "/ChefDepartement/archiverVacataire", method = RequestMethod.POST)
    public String archiver_Eleve(Long id){
        enseignantsService.archiver(id);
        return "redirect:/ChefDepartement/Enseignant";
    }
}
