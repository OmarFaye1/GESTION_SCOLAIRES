package uasz.sn.Gestion_Enseignement.Classes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Classes.modele.Matiere;
import uasz.sn.Gestion_Enseignement.Classes.service.MatiereService;

import java.util.List;

@Controller
@RequestMapping("/matieres")
public class MatiereController {

    @Autowired
    private MatiereService matiereService;

    // Afficher la liste des matières
    @GetMapping
    public String listerMatieres(Model model) {
        List<Matiere> matieres = matiereService.listerToutesMatieres();
        model.addAttribute("matieres", matieres);
        return "liste_matieres";
    }

    // Afficher le formulaire d'ajout d'une matière
    @GetMapping("/ajouter")
    public String afficherFormulaireAjout(Model model) {
        model.addAttribute("matiere", new Matiere());
        return "ajouter_matiere";
    }

    // Ajouter une matière
    @PostMapping("/ajouter")
    public String ajouterMatiere(@ModelAttribute Matiere matiere) {
        matiereService.ajouterMatiere(matiere);
        return "redirect:/matieres";
    }

    // Afficher le formulaire de modification d'une matière
    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        Matiere matiere = matiereService.trouverMatiereParId(id);
        model.addAttribute("matiere", matiere);
        return "modifier_matiere";
    }

    // Modifier une matière
    @PostMapping("/modifier/{id}")
    public String modifierMatiere(@PathVariable Long id, @ModelAttribute Matiere matiere) {
        matiere.setId(id);
        matiereService.modifierMatiere(matiere);
        return "redirect:/matieres";
    }

    // Supprimer une matière
    @GetMapping("/supprimer/{id}")
    public String supprimerMatiere(@PathVariable Long id) {
        matiereService.supprimerMatiere(id);
        return "redirect:/matieres";
    }
}