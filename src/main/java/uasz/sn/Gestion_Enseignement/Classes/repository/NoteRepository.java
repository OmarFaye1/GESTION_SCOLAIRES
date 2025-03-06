package uasz.sn.Gestion_Enseignement.Classes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uasz.sn.Gestion_Enseignement.Classes.modele.Note;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByEleveId(Long eleveId); // ✅ OK

    Note findByEleveIdAndMatiereId(Long eleveId, Long matiereId); // ✅ Correction ici
}
