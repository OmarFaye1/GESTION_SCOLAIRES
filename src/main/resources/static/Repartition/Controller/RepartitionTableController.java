package uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Service.RepartitionService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/repartition")
public class RepartitionTableController {

    @Autowired
    private RepartitionService repartitionService;

    /*@GetMapping("/table")
    public String afficherTableRepartition(Model model) {
        // Récupère toutes les informations nécessaires pour le tableau
        var listeRepartition = repartitionService.getAllRepartitions();

        // Ajouter les données à la vue
        model.addAttribute("repartitions", listeRepartition);

        return "repartitionTable"; // Vue Thymeleaf pour afficher le tableau
    }*/

    @GetMapping("/table")
    public String afficherTableRepartitions(Model model) {
        // Récupère toutes les informations nécessaires pour le tableau
        var listeRepartition = repartitionService.getRepartitionByType();
        // Ajouter les données à la vue
        model.addAttribute("repartitions", listeRepartition);
        return "repartitionTable"; // Vue Thymeleaf pour afficher le tableau
    }
    @GetMapping("/classe/{id}")
    public String getRepartitionByClasse(@PathVariable Long id, Model model) {
        List<Map<String, Object>> repartitions = repartitionService.getRepartitionByClasse(id);
        model.addAttribute("repartitions", repartitions);
        return "repartitionTable"; // La page Thymeleaf qui affiche la répartition
    }
}
