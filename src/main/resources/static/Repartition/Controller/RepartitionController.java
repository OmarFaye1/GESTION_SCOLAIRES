package uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele.Choix;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Service.RepartitionService;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele.Repartition;

import java.util.List;

@Controller
@RequestMapping("/rep")
public class RepartitionController {

    @Autowired
    private RepartitionService repartitionService;

    @GetMapping("/list")
    public String afficherRepartition(Model model) {
        List<Choix> choixList = repartitionService.getChoixTous();
        model.addAttribute("choixList", choixList);
        return "Repartition"; // Vue Thymeleaf
    }

    @PostMapping("/valider")
    public String validerChoix(@RequestParam Long choixId) {
        Choix choix = repartitionService.getChoixById(choixId);
        repartitionService.validerChoix(choix);

        return "redirect:/rep/list";
    }

    @PostMapping("/rejeter")
    public String rejeterChoix(@RequestParam Long choixId) {
        repartitionService.supprimerChoix(choixId);
        return "redirect:/rep/list";
    }

}
