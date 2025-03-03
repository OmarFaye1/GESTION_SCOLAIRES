package uasz.sn.Gestion_Enseignement.Classes.repository;

import uasz.sn.Gestion_Enseignement.Classes.modele.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatiereRepository extends JpaRepository<Matiere, Long> {
    // Trouver une matière par son code
    Matiere findByCode(String code);

    // Trouver une matière par son nom
    Matiere findByNom(String nom);
}