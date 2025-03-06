package uasz.sn.Gestion_Enseignement.Classes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Classes.modele.Classe;
import uasz.sn.Gestion_Enseignement.Classes.modele.Matiere;
import uasz.sn.Gestion_Enseignement.Classes.service.ClasseService;
import uasz.sn.Gestion_Enseignement.Classes.service.MatiereService;

import java.util.List;

@Controller
@RequestMapping("/chefDepartement/matiere")
public class MatiereController {

    @Autowired
    private MatiereService matiereService;

    @Autowired
    private ClasseService classeService;

    @GetMapping("/ajouter")
    public String afficherFormulaireAjoutMatiere(Model model) {
        model.addAttribute("matiere", new Matiere());
        model.addAttribute("classes", classeService.getAllClasses()); // Utiliser la méthode implémentée
        return "ajouter_matiere";
    }

    @PostMapping("/ajouter")
    public String ajouterMatiere(@ModelAttribute Matiere matiere, @RequestParam("classeId") Long classeId) {
        Classe classe = classeService.afficherClasse(classeId);
        matiere.setClasse(classe);
        matiereService.ajouterMatiere(matiere);
        return "redirect:/chefDepartement/matiere/liste";
    }


    @GetMapping("/liste")
    public String afficherListeMatieres(Model model) {
        model.addAttribute("matieres", matiereService.getAllMatieres());
        return "liste_matieres"; // Assurez-vous que ce fichier existe
    }


}