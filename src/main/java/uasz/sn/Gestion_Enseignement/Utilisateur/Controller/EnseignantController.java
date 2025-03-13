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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private PasswordEncoder passwordEncoder;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
    public String ajouter_Enseignant(@ModelAttribute Enseignant enseignant,
                                     @RequestParam String username,
                                     @RequestParam String motDePasse) {
        // Vérifiez ici si l'email n'est pas nul et qu'il est bien défini
        if (username == null || username.isEmpty()) {
            return "redirect:/errorPage"; // Redirigez vers une page d'erreur si l'email est manquant
        }

        // Log de l'email reçu
        System.out.println("Username: " + username);

        enseignant.setEmail( username);  // Assurez-vous que l'email est bien défini

        // Encodage du mot de passe
        String encodedPassword = passwordEncoder().encode(motDePasse);
        enseignant.setPassword(encodedPassword);
        enseignant.setDateCreation(new Date());

        // Ajout du rôle
        Role role = utilisateurService.ajouter_Role(new Role("Enseignant"));
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        enseignant.setRoles(roles);

        // Log des valeurs de l'objet Enseignant avant l'ajout
        System.out.println("Enseignant: " + enseignant);

        // Ajouter l'enseignant à la base de données
        enseignantService.ajouter(enseignant);
        return "redirect:/ChefDepartement/Enseignant";
    }
    @PostMapping("/supprimer")
    public String supprimerEnseignant(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            enseignantService.supprimer(id);
            redirectAttributes.addFlashAttribute("success", "Enseignant supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression !");
        }
        return "redirect:/chefDepartement/enseignants"; // Redirige vers la liste des enseignants
    }



    @RequestMapping(value = "/ChefDepartement/modifierEnseignant", method = RequestMethod.POST)
    public String modifier_Enseignant(Enseignant enseignant) {
        Enseignant enseignantModifie = enseignantService.rechercher(enseignant.getId());
        enseignantModifie.setMatricule(enseignant.getMatricule());
        enseignantModifie.setNom(enseignant.getNom());
        enseignantModifie.setPrenom(enseignant.getPrenom());
        enseignantService.modifier(enseignantModifie);
        return "redirect:/ChefDepartement/Enseignant";
    }

    @RequestMapping(value = "/ChefDepartement/activerEnseignant", method = RequestMethod.POST)
    public String activer_Enseignant(Long id) {
        enseignantService.activer(id);
        return "redirect:/ChefDepartement/Enseignant";
    }

    @RequestMapping(value = "/ChefDepartement/archiverEnseignant", method = RequestMethod.POST)
    public String archiver_Enseignant(Long id) {
        enseignantService.archiver(id);
        return "redirect:/ChefDepartement/Enseignant";
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