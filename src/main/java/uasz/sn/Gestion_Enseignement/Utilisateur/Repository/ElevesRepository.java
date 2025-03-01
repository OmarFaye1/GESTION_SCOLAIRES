package uasz.sn.Gestion_Enseignement.Utilisateur.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uasz.sn.Gestion_Enseignement.Utilisateur.Modele.Eleves;

@Repository
public interface ElevesRepository extends JpaRepository<Eleves, Long> {
}
