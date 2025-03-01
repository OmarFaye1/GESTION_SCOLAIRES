package uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele.Choix;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Utilisateur.Modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Modele.Classe;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Modele.EC;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChoixRepository extends JpaRepository<Choix, Long> {

    // Supprimer tous les choix associés à un même enseignement (même enseignementId)
    void deleteByEnseignementId(Long enseignementId);
     List<Choix> findByEnseignement(Enseignement enseignement);

    List<Choix> findByEnseignantId(Long enseignantId);

    @Modifying
    @Query("DELETE FROM Choix c WHERE c.enseignement.id = :enseignementId AND c.validated = false")
    void deleteNonValidesByEnseignementId(@Param("enseignementId") Long enseignementId);


    boolean existsByEnseignantAndEnseignementAndClasseAndSemestre(Enseignant enseignant, Enseignement enseignement, Classe classe, String semestre);

}
