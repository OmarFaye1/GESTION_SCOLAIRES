package uasz.sn.Gestion_Enseignement.Utilisateur.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Role;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Classes.modele.Eleve;
import uasz.sn.Gestion_Enseignement.Classes.modele.Matiere;
import uasz.sn.Gestion_Enseignement.Classes.service.EleveService; // ✅ Import du service EleveService
import uasz.sn.Gestion_Enseignement.Classes.service.MatiereService;
import uasz.sn.Gestion_Enseignement.Classes.service.NoteService;
import uasz.sn.Gestion_Enseignement.Utilisateur.Modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.Service.EnseignantService;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.*;

@Controller
public class EnseignantController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private EnseignantService enseignantService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private MatiereService matiereService;


    @Autowired
    private EleveService eleveService; // ✅ Injection du service pour gérer les élèves
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/Enseignant/Accueil", method = RequestMethod.GET)
    public String accueil_Enseignant(Model model, Principal principal) {
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));
        return "template_enseignant";
    }

    @RequestMapping(value = "/ChefDepartement/Accueil", method = RequestMethod.GET)
    public String accueil_ChefDepartement(Model model, Principal principal) {
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));
        return "template_ChefDepartement";
    }


    @RequestMapping(value = "/ChefDepartement/ajouterEnseignant", method = RequestMethod.POST)
    public String ajouter_Enseignant(@ModelAttribute Enseignant enseignant) {
        // Génération automatique du username
        String baseUsername = enseignant.getNom().substring(0, 1).toLowerCase() + enseignant.getPrenom().toLowerCase() + "@gmail.com";
        String username = generateUniqueUsername(enseignant.getNom(), enseignant.getPrenom());
        enseignant.setEmail(username);

        // Génération et encodage du mot de passe
        String motDePasse = generateRandomPassword();
        enseignant.setPassword(passwordEncoder.encode(motDePasse));
        enseignant.setDateCreation(new Date());

        // Ajout du rôle "Enseignant"
        Role role = new Role("Enseignant");
        enseignant.setRoles(Collections.singletonList(role));

        // Ajout dans la base de données
        enseignantService.ajouter(enseignant);

        return "redirect:/ChefDepartement/Enseignant";
    }


    @RequestMapping(value = "/supprimer", method = RequestMethod.POST)
    public String supprimerEnseignant(@RequestParam("id") Long enseignantId, RedirectAttributes redirectAttributes) {
        try {
            enseignantService.supprimer(enseignantId);
            redirectAttributes.addFlashAttribute("message", "Enseignant supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors de la suppression !");
        }

        return "redirect:/ChefDepartement/Enseignant"; // Redirection vers la liste
    }


    @RequestMapping(value = "/ChefDepartement/modifierEnseignant", method = RequestMethod.POST)
    public String modifierEnseignant(@ModelAttribute Enseignant enseignant) {
        System.out.println("🔄 Modification de l'enseignant avec ID : " + enseignant.getId());

        Enseignant enseignantModifie = enseignantService.rechercher(enseignant.getId());

        if (enseignantModifie == null) {
            System.out.println("❌ Enseignant non trouvé !");
            return "redirect:/ChefDepartement/Enseignant?erreur=notfound";
        }

        // Vérifier si le nom/prénom a changé
        boolean nomChange = !enseignantModifie.getNom().equalsIgnoreCase(enseignant.getNom());
        boolean prenomChange = !enseignantModifie.getPrenom().equalsIgnoreCase(enseignant.getPrenom());

        if (nomChange || !enseignantModifie.getPrenom().equals(enseignant.getPrenom())) {
            String nouveauUsername = generateUniqueUsername(enseignant.getNom(), enseignant.getPrenom());
            enseignantModifie.setUsername(nouveauUsername);

            // Génération d'un nouveau mot de passe sécurisé
            String nouveauPassword = generateRandomPassword();
            enseignantModifie.setPassword(passwordEncoder.encode(nouveauPassword));

            // Simuler l'envoi du mot de passe par e-mail
            System.out.println("📩 Nouveau mot de passe généré et envoyé : " + nouveauPassword);
        }

        // Mise à jour des autres champs
        enseignantModifie.setNom(enseignant.getNom());
        enseignantModifie.setPrenom(enseignant.getPrenom());
        enseignantModifie.setMatricule(enseignant.getMatricule());

        // Sauvegarde en base de données
        enseignantService.modifierEnseignant(enseignantModifie);
        System.out.println("✅ Enseignant mis à jour avec succès !");

        return "redirect:/ChefDepartement/Enseignant";
    }

    private String generateUniqueUsername(String nom, String prenom) {
        // Supposons que tu veuilles générer un username de la forme : "p.nom@domaine.com"
        String baseUsername = (prenom.charAt(0) + nom).toLowerCase().replaceAll("\\s+", "").replaceAll("[^a-zA-Z0-9]", "");
        String domaine = "@gmail.com"; // Tu peux changer cela selon ton besoin
        String username = baseUsername + "@" + "gmail.com";

        // Vérifier l'unicité du username
        int count = 1;
        while (enseignantService.usernameExists(username)) { // Cette méthode doit être créée dans le service
            username = baseUsername + count + "@domaine.com";
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


    @GetMapping("enseignant/saisirNotes")
    public String saisirNotes(@RequestParam("id") Long eleveId, Model model) {
        Eleve eleve = eleveService.rechercherEleveParId(eleveId);
        List<Matiere> matieres = matiereService.getAllMatieres(); // Récupérer les matières
        model.addAttribute("eleve", eleve);
        model.addAttribute("matieres", matieres); // Ajouter les matières au modèle
        return "saisir_notes";
    }


    @PostMapping("/enseignant/enregistrerNotes")
    public String enregistrerNotes(@RequestParam("eleveId") Long eleveId,
                                   @RequestParam("matiereId") Long matiereId,
                                   @RequestParam("noteDevoir") double noteDevoir,
                                   @RequestParam("noteComposition") double noteComposition,
                                   Model model) {
        noteService.enregistrerNote(eleveId, matiereId, noteDevoir, noteComposition);

        Eleve eleve = eleveService.rechercherEleveParId(eleveId);
        Matiere matiere = matiereService.getMatiereById(matiereId);

        model.addAttribute("eleve", eleve);
        model.addAttribute("matiere", matiere);
        model.addAttribute("noteDevoir", noteDevoir);
        model.addAttribute("noteComposition", noteComposition);

        return "confirmation_note";
    }

    @RequestMapping(value = "/Eleve/Accueil", method = RequestMethod.GET)
    public String accueil_Eleve(Model model, Principal principal) {
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));
        return "template_Eleve";
    }





}