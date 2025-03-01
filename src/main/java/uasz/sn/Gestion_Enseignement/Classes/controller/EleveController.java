package uasz.sn.Gestion_Enseignement.Classes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uasz.sn.Gestion_Enseignement.Classes.modele.Eleve;
import uasz.sn.Gestion_Enseignement.Classes.service.EleveService;

@Controller
public class EleveController {

    @Autowired
    private EleveService eleveService;

    @RequestMapping(value = "/ajouter_eleve_classe", method = RequestMethod.POST)
    public String ajouter_eleve_classe(Model model, Eleve eleve){
        eleveService.ajouterEleve(eleve); // ✅ Correction ici
        return "redirect:/details_classe?id="+ eleve.getClasse().getId();
    }

    @RequestMapping(value = "/modifier_eleve_classe", method = RequestMethod.POST)
    public String modifier_eleve_classe(Model model, Eleve eleve){
        eleveService.modifierEleve(eleve);
        return "redirect:/details_classe?id="+ eleve.getClasse().getId();
    }

    @RequestMapping(value = "/supprimer_eleve_classe", method = RequestMethod.POST)
    public String supprimer_eleve_classe(Model model, Eleve eleve){
        Long id = eleve.getClasse().getId();
        eleveService.supprimerEC(eleve);
        return "redirect:/details_classe?id="+id;
    }
}
