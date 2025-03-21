package uasz.sn.Gestion_Enseignement.Classes.repository;

import org.springframework.stereotype.Repository;
import uasz.sn.Gestion_Enseignement.Classes.modele.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.Modele.Enseignant;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {
    List<Matiere> findByClasseId(Long classeId);

    // ✅ Recherche d'une matière par son code
    //List<Matiere> findByEnseignant(Enseignant enseignant);
    // Recherche des matières par enseignant
    List<Matiere> findByEnseignantId(Long enseignantId);
}
