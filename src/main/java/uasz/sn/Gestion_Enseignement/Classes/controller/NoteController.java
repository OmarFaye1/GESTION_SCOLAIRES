package uasz.sn.Gestion_Enseignement.Classes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Classes.modele.Note;
import uasz.sn.Gestion_Enseignement.Classes.service.NoteService;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    // ✅ Afficher les notes d'un élève
    @GetMapping("/eleve/{eleveId}")
    public String listerNotesParEleve(@PathVariable Long eleveId, Model model) {
        List<Note> notes = noteService.trouverNotesParEleve(eleveId);
        model.addAttribute("notes", notes);
        model.addAttribute("eleveId", eleveId);
        return "liste_notes_eleve";
    }
}
