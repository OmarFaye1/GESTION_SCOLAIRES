package uasz.sn.Gestion_Enseignement.Utilisateur.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uasz.sn.Gestion_Enseignement.Utilisateur.Modele.Enseignant;

import java.util.Optional;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    @Query("SELECT e FROM Enseignant e WHERE e.matricule = ?1")
    Optional<Enseignant> findByMatricule(String matricule);
}