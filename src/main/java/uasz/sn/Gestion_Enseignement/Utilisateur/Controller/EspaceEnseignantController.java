package uasz.sn.Gestion_Enseignement.Utilisateur.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uasz.sn.Gestion_Enseignement.Classes.modele.Classe;
import uasz.sn.Gestion_Enseignement.Classes.modele.Eleve;
import uasz.sn.Gestion_Enseignement.Classes.service.ClasseService;
import uasz.sn.Gestion_Enseignement.Classes.service.EleveService;

import java.util.List;

@Controller
public class EspaceEnseignantController {

    @Autowired
    private ClasseService classeService;

    @Autowired
    private EleveService eleveService;

    // Afficher le formulaire principal de l'espace enseignant
    @GetMapping("/enseignant/espace")
    public String espaceEnseignant() {
        return "template_enseignant";
    }

    // Afficher les élèves de la classe demandée
    @PostMapping("/enseignant/afficherEleves")
    public String afficherEleves(@RequestParam("codeClasse") String codeClasse, Model model) {
        // Vérifier si la classe existe
        Classe classe = classeService.trouverClasseParCode(codeClasse);
        if (classe == null) {
            model.addAttribute("error", "Classe introuvable !");
            return "template_enseignant";
        }

        // Récupérer les élèves de cette classe
        List<Eleve> eleves = eleveService.afficherElevesParClasse(classe);
        model.addAttribute("eleves", eleves);
        model.addAttribute("classe", classe);
        return "template_enseignant";
    }
}
