package uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele.Choix;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele.TypeEnseignement;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Repository.ChoixRepository;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Repository.EnseignementRepository;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Service.ChoixService;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Service.EnseignementService;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Utilisateur.Modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Utilisateur.Repository.EnseignantRepository;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Utilisateur.Service.EnseignantService;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Modele.Classe;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Modele.EC;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Modele.Formation;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Modele.Maquette;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Service.ClasseService;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Service.ECService;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Service.MaquetteService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/choix")
public class ChoixController {

    @Autowired
    private ChoixService choixService;
    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private EnseignantService enseignantService;

    @Autowired
    private ECService ecService;

    @Autowired
    private EnseignementService enseignementService;

    @Autowired
    private ClasseService classeService;

    @PostMapping("/save")
    public String enregistrerChoix(
            @RequestParam(required = false) List<String> choix, // Liste des choix, avec un required=false pour éviter les erreurs si elle est vide
            @RequestParam Long enseignantId,
            @RequestParam Long classeId,
            @RequestParam String semestre) {

        System.out.println("Enseignant ID : " + enseignantId);
        System.out.println("Classe ID : " + classeId);
        System.out.println("Semestre : " + semestre);

        Enseignant enseignant = enseignantService.rechercherParId(enseignantId);
        Classe classe = classeService.afficherClasse(classeId);

        // Vérification si des choix ont été sélectionnés
        System.out.println("Récupération des choix...");
        if (choix != null && !choix.isEmpty()) {

            for (String choixStr : choix) {
                String[] parts = choixStr.split("_"); // Séparer l'ID et le type
                Long ecId = Long.parseLong(parts[0]);
                String type = parts[1]; // CM, TD, TP

                System.out.println("Choix récupéré : " + type);

                EC ec = ecService.afficherEC(ecId);
                if (ec == null) {
                    System.out.println("EC non trouvé pour ID: " + ecId);
                    continue;
                }

                TypeEnseignement typeEnum = TypeEnseignement.valueOf(type);
                Enseignement enseignement = enseignementService.findByEcAndTypeEnseignement(ec, typeEnum);

                if (enseignement == null) {
                    System.out.println("Enseignement non trouvé pour : " + ecId + " - " + type);
                    continue;
                }

                // Vérification si ce choix a déjà été effectué par le même enseignant
                boolean choixExistant = choixService.existsByEnseignantAndEnseignementAndClasseAndSemestre(
                        enseignant, enseignement, classe, semestre);

                if (choixExistant) {
                    System.out.println("Ce choix a déjà été fait par l'enseignant pour ce semestre. Ignoré.");
                    continue;  // Ne pas enregistrer le doublon
                }

                // Appeler la méthode pour mettre à jour l'enseignement sans la maquette
                enseignementService.mettreAJourEnseignement(ecId, typeEnum, classeId); // Mise à jour sans maquette

                Choix choixObj = new Choix();
                choixObj.setEnseignant(enseignant);
                choixObj.setEnseignement(enseignement);
                choixObj.setClasse(classe);
                choixObj.setSemestre(semestre);
                choixObj.setDateChoix(LocalDate.now());
                choixObj.setValidated(false);
                choixService.save(choixObj);

                System.out.println("Choix enregistré : " + enseignement.getLibelle());
            }
        } else {
            System.out.println("Aucun choix sélectionné.");
        }

        return "redirect:/choix/list";
    }


    // Méthode pour afficher les choix d'un enseignant et filtrer côté serveur
    @GetMapping("/list")
    public String afficherChoix(
            Model model,
            Principal principal) {

        // Récupérer le nom d'utilisateur à partir du principal (authentification)
        String username = principal.getName();  // Nom d'utilisateur de l'enseignant connecté

        // Récupérer l'utilisateur à partir du nom d'utilisateur
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(username);

        if (utilisateur == null) {
            model.addAttribute("error", "Utilisateur non trouvé.");
            return "error";  // Ou rediriger vers une page d'erreur
        }

        // Vérifier si l'utilisateur est un enseignant
        if (!(utilisateur instanceof Enseignant)) {
            model.addAttribute("error", "L'utilisateur n'est pas un enseignant.");
            return "error";  // Ou rediriger vers une page d'erreur
        }

        Enseignant enseignant = (Enseignant) utilisateur;  // Cast de l'utilisateur en enseignant
        System.out.println("Enseignant connecté : " + enseignant);

        // Récupérer tous les choix de l'enseignant (sans filtrage sur l'enseignement)
        List<Choix> choixList = choixService.rechercherParEnseignant(enseignant.getId());

        // Si les choix sont non vides, récupérer la formation et la classe
        if (!choixList.isEmpty()) {
            // Récupérer la formation et la classe à partir du premier choix (vous pouvez ajuster cette logique selon votre besoin)
            Formation formation = choixList.get(0).getClasse().getFormation();
            Classe classe = choixList.get(0).getClasse();

            // Ajouter la formation et la classe au modèle
            model.addAttribute("formationId", formation.getId());
            model.addAttribute("classeId", classe.getId());
        } else {
            model.addAttribute("message", "Vous n'avez pas encore fait de choix.");

        }

        // Ajouter les données au modèle
        model.addAttribute("enseignant", enseignant);
        model.addAttribute("choixList", choixList);

        return "choix";  // Nom de la vue qui affichera les choix
    }

    //suppresion de choix
    @PostMapping("/supprimer")
    public String supprimerChoix(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            // Récupérer le choix existant
            Choix choixExistant = choixService.rechercherParId(id);
            if (choixExistant == null || choixExistant.getValidated()) {
                redirectAttributes.addFlashAttribute("error", "Suppresion impossible, choix validé !");
                return "redirect:/choix/list";
            }
            //suppression
            choixService.supprimerChoix(id); // Assurez-vous d'avoir cette méthode dans ton service
            redirectAttributes.addFlashAttribute("message", "Choix supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression du choix !");
        }
        return "redirect:/choix/list"; // Remplace par l'URL correcte de la page
    }

    //modifier choix post
    @PostMapping("/modifier")
    public String modifierChoix(@RequestParam Long id, @RequestParam(required = false) List<String> typeEnseignement, RedirectAttributes redirectAttributes) {
        System.out.println("modification de choix");
        // Recherche du choix par ID
        Choix choix = choixService.rechercherParId(id);
        System.out.println("modification de choix trouvé");

        if (choix == null) {
            redirectAttributes.addFlashAttribute("error", "Choix introuvable !");
            return "redirect:/choix/list";
        }

        // Vérification si le choix est validé
        if (choix.getValidated()) {
            redirectAttributes.addFlashAttribute("error", "Modification impossible, choix déjà validé !");
            return "redirect:/choix/list";
        }
        System.out.println("modification de choix");

        // Modification du type d'enseignement si le choix n'est pas validé
        if (typeEnseignement != null && !typeEnseignement.isEmpty()) {
            // On prend le premier type sélectionné (en supposant que l'utilisateur ne peut en sélectionner qu'un seul)
            String typeSelectionne = typeEnseignement.get(0);
            TypeEnseignement typeEnum = TypeEnseignement.valueOf(typeSelectionne);
            System.out.println(" choix modifié");

            // Met à jour le type d'enseignement
            choix.getEnseignement().setTypeEnseignement(typeEnum);

            // Mise à jour de la date de modification
            choix.setDateChoix(LocalDate.now());
            System.out.println(" choix enregistré");

            // Sauvegarde des modifications
            choixService.save(choix);
        }

        // Retour avec un message de succès
        redirectAttributes.addFlashAttribute("message", "Choix modifié avec succès !");
        return "redirect:/choix/list";
    }

}

