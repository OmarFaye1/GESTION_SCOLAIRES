package uasz.sn.Gestion_Enseignement.Classes.service;

import uasz.sn.Gestion_Enseignement.Classes.modele.Note;
import uasz.sn.Gestion_Enseignement.Classes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    // Ajouter ou mettre à jour une note
    public Note enregistrerNote(Note note) {
        return noteRepository.save(note);
    }

    // Supprimer une note
    public void supprimerNote(Long id) {
        noteRepository.deleteById(id);
    }

    // Trouver une note par son ID
    public Note trouverNoteParId(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    // Trouver toutes les notes d'un élève
    public List<Note> trouverNotesParEleve(Long eleveId) {
        return noteRepository.findByEleveId(eleveId);
    }

    // Trouver toutes les notes d'une matière
    public List<Note> trouverNotesParMatiere(Long matiereId) {
        return noteRepository.findByMatiereId(matiereId);
    }

    // Trouver une note spécifique pour un élève et une matière
    public Note trouverNoteParEleveEtMatiere(Long eleveId, Long matiereId) {
        return noteRepository.findByEleveIdAndMatiereId(eleveId, matiereId);
    }
}