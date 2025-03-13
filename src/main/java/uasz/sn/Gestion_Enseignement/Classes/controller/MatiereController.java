package uasz.sn.Gestion_Enseignement.Classes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Classes.modele.Classe;
import uasz.sn.Gestion_Enseignement.Classes.modele.Matiere;
import uasz.sn.Gestion_Enseignement.Classes.service.ClasseService;
import uasz.sn.Gestion_Enseignement.Classes.service.MatiereService;
import uasz.sn.Gestion_Enseignement.Utilisateur.Modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.Service.EnseignantService;

@Controller
@RequestMapping("/chefDepartement/matiere")
public class MatiereController {

    @Autowired
    private MatiereService matiereService;

    @Autowired
    private ClasseService classeService;

    @Autowired
    private EnseignantService enseignantService;

    @GetMapping("/ajouter")
    public String afficherFormulaireAjoutMatiere(Model model) {
        model.addAttribute("matiere", new Matiere());
        model.addAttribute("classes", classeService.getAllClasses()); // ✅ Liste des classes
        model.addAttribute("enseignants", enseignantService.liste()); // ✅ Liste des enseignants
        return "ajouter_matiere";
    }

    @PostMapping("/ajouter")
    public String ajouterMatiere(@ModelAttribute Matiere matiere,
                                 @RequestParam("enseignantId") Long enseignantId,
                                 @RequestParam("classeId") Long classeId) {

        // Assigner l'enseignant sélectionné
        Enseignant enseignant = enseignantService.rechercher(enseignantId);
        matiere.setEnseignant(enseignant);

        // Assigner la classe sélectionnée
        Classe classe = classeService.afficherClasse(classeId);
        matiere.setClasse(classe);

        // Ajouter la matière
        matiereService.ajouterMatiere(matiere);

        return "redirect:/chefDepartement/matiere/liste";
    }

    @GetMapping("/liste")
    public String afficherListeMatieres(Model model) {
        model.addAttribute("matieres", matiereService.getAllMatieres());
        return "liste_matieres";
    }
}
