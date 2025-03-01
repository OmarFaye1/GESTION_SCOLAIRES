package uasz.sn.Gestion_Enseignement.Authentification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    @Query("SELECT u FROM Utilisateur u WHERE u.username = :username")
    Utilisateur findUtilisateurByUsername(@Param("username") String username);
}
