package uasz.sn.Gestion_Enseignement.Classes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uasz.sn.Gestion_Enseignement.Classes.modele.Classe;
import uasz.sn.Gestion_Enseignement.Classes.modele.Eleve;
import uasz.sn.Gestion_Enseignement.Classes.service.ClasseService;
import uasz.sn.Gestion_Enseignement.Classes.service.EleveService;

import java.util.List;

@Controller
public class ClasseController {

    @Autowired
    private EleveService eleveService; // Injection du service Eleve

    @Autowired
    private ClasseService classeService; // Injection du service Classe

    @RequestMapping(value = "/classe", method = RequestMethod.GET)
    public String lister_classe(Model model) {
        List<Classe> classeList = classeService.afficherToutClasse(); // Récupérer toutes les classes
        model.addAttribute("listeDesClasse", classeList);
        return "classe";
    }

    @RequestMapping(value = "/ajouter_classe", method = RequestMethod.POST)
    public String ajouter_classe(Model model, Classe classe) {
        classeService.ajouterClasse(classe);
        return "redirect:/classe";
    }

    @RequestMapping(value = "/modifier_classe", method = RequestMethod.POST)
    public String modifier_classe(Model model, Classe classe) {
        classeService.modifierClasse(classe);
        return "redirect:/classe";
    }

    @RequestMapping(value = "/supprimer_classe", method = RequestMethod.POST)
    public String supprimer_classe(Model model, Classe classe) {
        classeService.supprimerClasse(classe);
        return "redirect:/classe";
    }

    @RequestMapping(value = "/details_classe", method = RequestMethod.GET)
    public String details_classe(Model model, @RequestParam("id") Long id) {
        Classe classe = classeService.afficherClasse(id); // Récupérer la classe par ID
        List<Eleve> eleveList = eleveService.afficherElevesParClasse(classe); // Appel de la nouvelle méthode

        Eleve newEleve = new Eleve();
        newEleve.setClasse(classe);

        model.addAttribute("classe", classe);
        model.addAttribute("listeDesEleve", eleveList); // Ajouter la liste des élèves
        model.addAttribute("newEleve", newEleve);
        return "details_classe";
    }
}
