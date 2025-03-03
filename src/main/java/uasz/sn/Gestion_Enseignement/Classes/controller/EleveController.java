package uasz.sn.Gestion_Enseignement.Classes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Classes.modele.Eleve;
import uasz.sn.Gestion_Enseignement.Classes.service.EleveService;

@Controller
@RequestMapping("/eleve")
public class EleveController {

    @Autowired
    private EleveService eleveService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Ajouter un élève
    @PostMapping("/ajouter")
    public String ajouterEleve(@ModelAttribute Eleve eleve) {
        if (eleve.getUsername() == null || eleve.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Le champ username ne peut pas être vide.");
        }
        if (eleve.getPassword() == null || eleve.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide.");
        }

        // ✅ Hasher le mot de passe avant insertion
        eleve.setPassword(passwordEncoder.encode(eleve.getPassword()));

        eleveService.ajouterEleve(eleve);
        return "redirect:/details_classe?id=" + eleve.getClasse().getId();
    }

    // ✅ Modifier un élève
    @PostMapping("/modifier")
    public String modifierEleve(@ModelAttribute Eleve eleve) {
        eleveService.modifierEleve(eleve);
        return "redirect:/details_classe?id=" + eleve.getClasse().getId();
    }

    // ✅ Supprimer un élève
    @PostMapping("/supprimer")
    public String supprimerEleve(@RequestParam("id") Long eleveId, @RequestParam("classeId") Long classeId) {
        eleveService.supprimerEleve(eleveId);
        return "redirect:/details_classe?id=" + classeId;
    }
}
