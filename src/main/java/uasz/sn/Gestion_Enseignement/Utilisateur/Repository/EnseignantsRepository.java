package uasz.sn.Gestion_Enseignement.Utilisateur.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uasz.sn.Gestion_Enseignement.Utilisateur.Modele.Enseignants;

import java.util.Optional;

@Repository
public interface EnseignantsRepository extends JpaRepository <Enseignants, Long> {
    Optional<Enseignants> findByUsername(String username); // Nouvelle méthode

}
