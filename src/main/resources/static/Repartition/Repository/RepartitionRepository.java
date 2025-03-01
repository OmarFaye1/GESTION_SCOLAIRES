package uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele.Repartition;

import java.util.List;

@Repository
public interface RepartitionRepository extends JpaRepository<Repartition, Long> {
    // Supprimer toutes les répartitions d'un enseignement spécifique
    @Modifying
    @Query("DELETE FROM Choix c WHERE c.enseignement.id = :enseignementId ")
    void deleteByEnseignementId(@Param("enseignementId") Long enseignementId);

    List<Repartition> findByClasseId(Long classeId);

}

