package uasz.sn.Gestion_Enseignement.Authentification.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Role;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.repository.RoleRepository;
import uasz.sn.Gestion_Enseignement.Authentification.repository.UtilisateurRepository;

@Service
@Transactional
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Utilisateur ajouter_Utilisateur(Utilisateur utilisateur){
        utilisateurRepository.save(utilisateur);
        return utilisateur;
    }

    public Role ajouter_Role(Role role){
        roleRepository.save(role);
        return role;
    }

    public  void ajouter_UtilisateurRoles(Utilisateur utilisateur, Role role){
        Utilisateur user = utilisateurRepository.findUtilisateurByUsername(utilisateur.getUsername());
        Role profil = roleRepository.findRoleByRole(role.getRole());
        user.getRoles().add(profil);
    }

    public Utilisateur rechercher_Utilisateur(String username){
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByUsername((username));
        return  utilisateur;
    }
}
