package uasz.sn.Gestion_Enseignement.Authentification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Role;

public interface RoleRepository extends JpaRepository<Role, String>  {
    @Query("SELECT r FROM Role r WHERE r.role = :role")
    Role findRoleByRole (@Param("role") String Role);
}
