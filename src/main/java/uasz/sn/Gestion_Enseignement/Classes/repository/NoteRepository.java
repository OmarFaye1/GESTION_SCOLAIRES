package uasz.sn.Gestion_Enseignement.Classes.repository;

import uasz.sn.Gestion_Enseignement.Classes.modele.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    // Trouver toutes les notes d'un élève
    List<Note> findByEleveId(Long eleveId);

    // Trouver toutes les notes d'une matière
    List<Note> findByMatiere(String matiere);

    // Trouver une note spécifique pour un élève et une matière
    Note findByEleveIdAndMatiere(Long eleveId, String matiere);


}