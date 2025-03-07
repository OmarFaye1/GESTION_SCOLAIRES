package uasz.sn.Gestion_Enseignement.Classes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Role;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Classes.modele.Classe;
import uasz.sn.Gestion_Enseignement.Classes.modele.Eleve;
import uasz.sn.Gestion_Enseignement.Classes.service.ClasseService; // ✅ Ajout du service manquant
import uasz.sn.Gestion_Enseignement.Classes.service.EleveService;

import java.security.Principal;
import java.util.ArrayList;

@Controller
@RequestMapping("/eleve")
public class EleveController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private EleveService eleveService;

    @Autowired
    private ClasseService classeService; // ✅ Ajout de la déclaration et injection

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/ajouter")
    public String ajouterEleve(@ModelAttribute Eleve eleve, @RequestParam("classeId") Long classeId) {
        if (eleve.getUsername() == null || eleve.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Le champ username ne peut pas être vide.");
        }
        if (eleve.getPassword() == null || eleve.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide.");
        }

        // ✅ Hasher le mot de passe avant insertion
        eleve.setPassword(passwordEncoder.encode(eleve.getPassword()));

        // ✅ Assigner la classe à l'élève
        Classe classe = classeService.afficherClasse(classeId);
        eleve.setClasse(classe);

        // ✅ Vérifier si la liste des rôles est null et l'initialiser
        if (eleve.getRoles() == null) {
            eleve.setRoles(new ArrayList<>()); // Initialisation de la liste
        }

        // ✅ Ajouter le rôle "Eleve"
        Role roleEleve = utilisateurService.ajouter_Role(new Role("Eleve"));
        eleve.getRoles().add(roleEleve); // Maintenant, la liste n'est plus null

        // ✅ Sauvegarder l'élève
        eleveService.ajouterEleve(eleve);

        return "redirect:/details_classe?id=" + eleve.getClasse().getId();
    }


    // ✅ Modifier un élève
    @PostMapping("/modifier")
    public String modifierEleve(@ModelAttribute Eleve eleve) {
        eleveService.modifierEleve(eleve);
        return "redirect:/details_classe?id=" + eleve.getClasse().getId();
    }

    // ✅ Supprimer un élève
    @PostMapping("/supprimer")
    public String supprimerEleve(@RequestParam("id") Long eleveId, @RequestParam("classeId") Long classeId) {
        eleveService.supprimerEleve(eleveId);
        return "redirect:/details_classe?id=" + classeId;
    }

    // ✅ Accueil de l'élève
    @GetMapping("Eleve/Accueil")
    public String accueil_Eleve(Model model, Principal principal) {
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));
        return "template_Eleve"; // Assurez-vous que ce fichier existe dans templates/
    }
}
