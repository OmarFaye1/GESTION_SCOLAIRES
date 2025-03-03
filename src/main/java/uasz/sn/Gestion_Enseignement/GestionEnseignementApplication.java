package uasz.sn.Gestion_Enseignement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Role;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Utilisateur.Modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.Service.EnseignantsService;
import uasz.sn.Gestion_Enseignement.Classes.modele.Eleve; // ✅ Import ajouté pour Eleve

import java.util.Date;

@SpringBootApplication
public class GestionEnseignementApplication implements CommandLineRunner {

	private final UtilisateurService utilisateurService;
	private final EnseignantsService enseignantsService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public GestionEnseignementApplication(UtilisateurService utilisateurService,
										  EnseignantsService enseignantsService,
										  PasswordEncoder passwordEncoder) {
		this.utilisateurService = utilisateurService;
		this.enseignantsService = enseignantsService;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(GestionEnseignementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Ajout des rôles
		/*Role enseignantRole = utilisateurService.ajouter_Role(new Role("Enseignant"));

		Role chefDepartementRole = utilisateurService.ajouter_Role(new Role("ChefDepartement"));

		// Mot de passe crypté
		String password = passwordEncoder.encode("Passer123");

		// Création et ajout d'un enseignant
		Enseignant enseignant = createEnseignant("NDOYE", "Malick", "mndoye@uasz.sn", "ID2024", password);
		enseignantsService.ajouter(enseignant);
		utilisateurService.ajouter_UtilisateurRoles(enseignant, enseignantRole);


		// Création et ajout d'un chef de département
		Enseignant chefDepartement = createEnseignant("GAYE", "Cheikh", "cgaye@uasz.sn", "SD2024", password);
		enseignantsService.ajouter(chefDepartement);
		utilisateurService.ajouter_UtilisateurRoles(chefDepartement, chefDepartementRole);
	}

	// ✅ Méthode utilitaire pour créer un enseignant
	private Enseignant createEnseignant(String nom, String prenom, String username, String matricule, String password) {
		Enseignant enseignant = new Enseignant();
		enseignant.setNom(nom);
		enseignant.setPrenom(prenom);
		enseignant.setUsername(username);
		enseignant.setPassword(password);
		enseignant.setActive(true);
		enseignant.setMatricule(matricule);
		enseignant.setDateCreation(new Date());
		return enseignant;*/
	}


}
