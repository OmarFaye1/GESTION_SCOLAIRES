package uasz.sn.Gestion_Enseignement.Classes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uasz.sn.Gestion_Enseignement.Classes.modele.Eleve;
import uasz.sn.Gestion_Enseignement.Classes.modele.Classe;

import java.util.List;

public interface EleveRepository extends JpaRepository<Eleve, Long> {
    // Méthode pour récupérer les élèves d'une classe spécifique
    List<Eleve> findByClasse(Classe classe);
    // Ajouter la méthode pour vérifier si un username existe
    boolean existsByUsername(String username);
}