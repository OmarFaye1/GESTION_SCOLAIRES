package uasz.sn.Gestion_Enseignement.Utilisateur.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Role;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Classes.modele.Eleve;
import uasz.sn.Gestion_Enseignement.Classes.service.EleveService; // ✅ Import du service EleveService
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
    public String ajouter_Enseignant(Enseignant enseignant) {
        String password = passwordEncoder().encode("Passer123");
        enseignant.setPassword(password);
        enseignant.setDateCreation(new Date());
        Role role = utilisateurService.ajouter_Role(new Role("Enseignant"));
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        enseignant.setRoles(roles);
        enseignantService.ajouter(enseignant);
        return "redirect:/ChefDepartement/Enseignant";
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

    // ✅ Ajout de l'injection correcte du service EleveService pour éviter l'erreur
    @GetMapping("enseignant/saisirNotes")
    public String saisirNotes(@RequestParam("id") Long eleveId, Model model) {
        // Charger l'élève à partir de l'ID
        Eleve eleve = eleveService.rechercherEleveParId(eleveId);
        model.addAttribute("eleve", eleve);

        return "saisir_notes"; // Assure-toi que ce fichier existe
    }

    @Autowired
    private NoteService noteService; // Assurez-vous d'avoir un service pour gérer les notes

    @PostMapping("enseignant/enregistrerNotes")
    public String enregistrerNotes(@RequestParam("eleveId") Long eleveId,
                                   @RequestParam("matiere") String matiere,
                                   @RequestParam("note") double valeurNote) {
        // Enregistrer la note
        noteService.enregistrerNote(eleveId, matiere, valeurNote);

        return "redirect:/enseignant/espace"; // Redirige vers l'espace enseignant
    }
}
