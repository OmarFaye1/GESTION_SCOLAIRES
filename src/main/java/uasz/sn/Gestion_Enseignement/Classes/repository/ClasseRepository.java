package uasz.sn.Gestion_Enseignement.Classes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uasz.sn.Gestion_Enseignement.Classes.modele.Classe;
import uasz.sn.Gestion_Enseignement.Classes.modele.Eleve;

import java.util.List;
import java.util.Optional;

public interface ClasseRepository extends JpaRepository<Classe, Long> {
    @Query("SELECT e FROM Eleve e WHERE e.classe = ?1")
    List<Eleve> findByClasse(Classe classe);

    Optional<Classe> findByCode(String code);
}
