package uasz.sn.Gestion_Enseignement.Classes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uasz.sn.Gestion_Enseignement.Classes.modele.Classe;
import uasz.sn.Gestion_Enseignement.Classes.modele.Eleve;
import java.util.List;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {

    @Query("SELECT e FROM Eleve e WHERE e.classe = ?1") // ✅ Requête correcte
    List<Eleve> findByClasse(Classe classe); // ✅ Renommé pour correspondre à `Eleve`
}
