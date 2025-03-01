package uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.Repartition.Modele.TypeEnseignement;
import uasz.sn.Gestion_Enseignement.Projet_Devoir.maquette.Modele.EC;

import java.util.List;

@Repository
public interface EnseignementRepository extends JpaRepository<Enseignement, Long> {
    List<Enseignement> findByEcId(Long ecId);
    Enseignement findByEcAndTypeEnseignement(EC ec, TypeEnseignement typeEnseignement);
    boolean existsByEcAndTypeEnseignement(EC ec, TypeEnseignement type);


}
