package uasz.sn.Gestion_Enseignement.Classes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Classes.modele.Note;
import uasz.sn.Gestion_Enseignement.Classes.repository.NoteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    // ✅ Trouver les notes d'un élève
    public List<Note> trouverNotesParEleve(Long eleveId) {
        return noteRepository.findByEleveId(eleveId);
    } // ✅ Fermeture correcte de la méthode

    // ✅ Trouver une note par ID
    public Note trouverNoteParId(Long id) {
        Optional<Note> note = noteRepository.findById(id);
        return note.orElseThrow(() -> new RuntimeException("Note introuvable avec l'ID : " + id));
    }

    // ✅ Enregistrer une note
    public void enregistrerNote(Note note) {
        noteRepository.save(note);
    }

    // ✅ Enregistrer une note avec les deux valeurs (Devoir et Composition)
    public void enregistrerNote(Long eleveId, String matiere, double noteDevoir, double noteComposition) {
        Note note = new Note();
        note.setEleveId(eleveId);
        note.setMatiere(matiere);
        note.setNoteDevoir(noteDevoir);
        note.setNoteComposition(noteComposition);

        noteRepository.save(note);
    }

    // ✅ Supprimer une note
    public void supprimerNote(Long id) {
        noteRepository.deleteById(id);
    }
}
