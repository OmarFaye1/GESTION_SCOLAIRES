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

    // Afficher toutes les notes d'un élève
    @GetMapping("/eleve/{eleveId}")
    public String listerNotesParEleve(@PathVariable Long eleveId, Model model) {
        List<Note> notes = noteService.trouverNotesParEleve(eleveId);
        model.addAttribute("notes", notes);
        model.addAttribute("eleveId", eleveId);
        return "liste_notes_eleve";
    }

    // Afficher le formulaire pour ajouter ou modifier une note
    @GetMapping("/ajouter/{eleveId}")
    public String afficherFormulaireNote(@PathVariable Long eleveId, Model model) {
        model.addAttribute("note", new Note());
        model.addAttribute("eleveId", eleveId);
        return "ajouter_note";
    }

    // Enregistrer une note
    @PostMapping("/ajouter/{eleveId}")
    public String enregistrerNote(@PathVariable Long eleveId, @ModelAttribute Note note) {
        note.setEleveId(eleveId);
        noteService.enregistrerNote(note);
        return "redirect:/notes/eleve/" + eleveId;
    }

    // Supprimer une note
    @GetMapping("/supprimer/{id}")
    public String supprimerNote(@PathVariable Long id) {
        Note note = noteService.trouverNoteParId(id);
        Long eleveId = note.getEleveId();
        noteService.supprimerNote(id);
        return "redirect:/notes/eleve/" + eleveId;
    }
}