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
import java.security.SecureRandom;
import java.util.*;

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

    // ✅ Générer un username unique
    private String generateUniqueUsername(String prenom, String nom) {
        String baseUsername = (prenom.charAt(0) + nom).toLowerCase() + "@gmail.com";
        String username = baseUsername;
        int count = 1;

        while (eleveService.usernameExists(username)) {
            username = (prenom.charAt(0) + nom).toLowerCase() + count + "@gmail.com";
            count++;
        }

        return username;
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&*!";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }

        return password.toString();
    }
    @RequestMapping(value = "/ajouter", method = RequestMethod.POST)
    public String ajouterEleve(@ModelAttribute Eleve eleve, @RequestParam("classeId") Long classeId) {
        // Vérification de la classe
        Classe classe = classeService.afficherClasse(classeId);
        eleve.setClasse(classe);

        // Générer un username unique
        String username = generateUniqueUsername(eleve.getPrenom(), eleve.getNom());
        eleve.setUsername(username);

        // Générer un mot de passe aléatoire
        String rawPassword = generateRandomPassword();
        System.out.println("Generated raw password: " + rawPassword); // Vérification du mot de passe généré
        eleve.setPassword(passwordEncoder.encode(rawPassword)); // Hasher le mot de passe

        // Initialiser les rôles
        if (eleve.getRoles() == null) {
            eleve.setRoles(new ArrayList<>());
        }

        Role roleEleve = utilisateurService.ajouter_Role(new Role("Eleve"));
        eleve.getRoles().add(roleEleve);

        // Sauvegarde de l'élève
        eleveService.ajouterEleve(eleve);

        return "redirect:/details_classe?id=" + eleve.getClasse().getId();
    }


    @RequestMapping(value = "/modifier", method = RequestMethod.POST)
    public String modifierEleve(@ModelAttribute Eleve eleve, @RequestParam(value = "classeId", required = false) Long classeId) {
        // Si une nouvelle classe est envoyée, l'affecter
        if (eleve.getClasse() == null && classeId != null) {
            Classe classe = classeService.afficherClasse(classeId);
            if (classe != null) {
                eleve.setClasse(classe);
            }
        }
        // Générer le username automatiquement : première lettre du prénom + nom complet + @gmail.com
        if (eleve.getNom() != null && eleve.getPrenom() != null) {
            // Génération du username : première lettre du prénom + nom complet + @gmail.com
            String generatedUsername = eleve.getPrenom().substring(0, 1).toLowerCase() + eleve.getNom().toLowerCase() + "@gmail.com";
            eleve.setUsername(generatedUsername);
        }

        // Si le nom ou le prénom ont été modifiés, générer un nouveau mot de passe
        if (eleve.getNom() != null && eleve.getPrenom() != null) {
            // Générer un mot de passe basé sur le prénom et nom (par exemple, prénom+nom+timestamp)
            String generatedPassword = eleve.getPrenom().toLowerCase() + eleve.getNom().toLowerCase() + System.currentTimeMillis();
            eleve.setPassword(generatedPassword);
        }

        // Hasher le mot de passe avant de le sauvegarder
        eleve.setPassword(passwordEncoder.encode(eleve.getPassword()));

        // Appel au service pour modifier l'élève
        eleveService.modifierEleve(eleve);

        // Redirection
        if (eleve.getClasse() != null) {
            return "redirect:/details_classe?id=" + eleve.getClasse().getId();
        } else {
            return "redirect:/eleve/liste";  // Assurez-vous d'avoir une route pour afficher la liste des élèves
        }
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

    // Méthode pour afficher les élèves par classe
    @RequestMapping(value = "/afficherParClasse", method = RequestMethod.GET)
    public String afficherElevesParClasse(@RequestParam("classeId") Long classeId, Model model) {
        // Récupérer la classe à partir de son ID
        Classe classe = classeService.afficherClasse(classeId);
        if (classe == null) {
            model.addAttribute("error", "Classe non trouvée");
            return "error_page"; // Assurez-vous d'avoir une vue pour gérer l'erreur
        }

        // Appeler le service pour obtenir la liste des élèves de la classe
        List<Eleve> eleves = eleveService.afficherElevesParClasse(classe);
        model.addAttribute("listeDesEleve", eleves);
        model.addAttribute("classe", classe);

        // Retourner le nom de la vue qui affichera la liste des élèves
        return "details_classe"; // Assurez-vous d'avoir cette vue (liste_eleves_classe.html)
    }


}
