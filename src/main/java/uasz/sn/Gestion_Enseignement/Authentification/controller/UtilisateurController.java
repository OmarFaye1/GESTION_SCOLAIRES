package uasz.sn.Gestion_Enseignement.Authentification.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;

import java.security.Principal;

@Controller
public class UtilisateurController {
    @Autowired
    private UtilisateurService utilisateurService;

    @RequestMapping(value = "/login")
    public String index(){
        return "login";
    }

    @RequestMapping("/")
    public  String login(Principal principal){
        String url = "login";
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        if (utilisateur.getRoles().get(0).getRole().equals("Permanent")){
            url = "redirect:/Permanent/Accueil ";
        } else if (utilisateur.getRoles().get(0).getRole().equals("Vacataire")) {
            url = "redirect:/Vacataire/Accueil ";
        } else if (utilisateur.getRoles().get(0).getRole().equals("ChefDepartement")) {
            url = "redirect:/ChefDepartement/Accueil ";
        } else if (utilisateur.getRoles().get(0).getRole().equals("Etudiant")) {
            url = "redirect:/Etudiant/Accueil ";
        }else if (utilisateur.getRoles().get(0).getRole().equals("ResponsableClasse")) {
            url = "redirect:/ResponsableClasse/Accueil ";
        }
        return url;
    }

        @RequestMapping(value = "/logout")
    public String logOutAndRedirectToLoginPage(Authentication authentication,
                                               HttpServletRequest request,
                                               HttpServletResponse response){
        if (authentication != null ){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout=true";
    }

}
