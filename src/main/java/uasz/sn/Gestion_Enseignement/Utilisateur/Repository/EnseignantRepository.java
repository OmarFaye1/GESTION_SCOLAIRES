package uasz.sn.Gestion_Enseignement.Utilisateur.Repository;

import uasz.sn.Gestion_Enseignement.Utilisateur.Modele.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {

    Optional<Enseignant> findByMatricule(String matricule);
    List<Enseignant> findByArchiveTrue();
    Optional<Enseignant> findByUsername(String username);
}
