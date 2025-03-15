package uasz.sn.Gestion_Enseignement.Classes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Classes.modele.Eleve;
import uasz.sn.Gestion_Enseignement.Classes.modele.Matiere;
import uasz.sn.Gestion_Enseignement.Classes.modele.Note;
import uasz.sn.Gestion_Enseignement.Classes.repository.NoteRepository;
import uasz.sn.Gestion_Enseignement.Classes.service.EleveService;
import uasz.sn.Gestion_Enseignement.Classes.service.MatiereService;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private EleveService eleveService; // ✅ Ajout du service Eleve

    @Autowired
    private MatiereService matiereService; // ✅ Ajout du service Matiere

    public List<Note> trouverNotesParEleve(Long eleveId)   {return noteRepository.findByEleveId(eleveId) ; }

    // ✅ Trouver une note par ID
    public Note trouverNoteParId(Long id) {
        Optional<Note> note = noteRepository.findById(id);
        return note.orElseThrow(() -> new RuntimeException("Note introuvable avec l'ID : " + id));
    }

    // ✅ Enregistrer une note avec un objet Note
    public void enregistrerNote(Note note) {
        noteRepository.save(note);
    }

    public void enregistrerNote(Long eleveId, Long matiereId, double noteDevoir, double noteComposition) {
        Eleve eleve = eleveService.rechercherEleveParId(eleveId);
        Matiere matiere = matiereService.getMatiereById(matiereId);

        if (eleve == null || matiere == null) {
            throw new RuntimeException("Élève ou Matière introuvable !");
        }

        Note note = new Note();
        note.setEleve(eleve); // ✅ Correction ici
        note.setMatiere(matiere); // ✅ Correction ici
        note.setNoteDevoir(noteDevoir);
        note.setNoteComposition(noteComposition);

        noteRepository.save(note);
    }


    }




