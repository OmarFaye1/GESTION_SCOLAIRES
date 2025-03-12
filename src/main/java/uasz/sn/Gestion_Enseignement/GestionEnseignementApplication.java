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
import uasz.sn.Gestion_Enseignement.Classes.modele.Eleve;
import uasz.sn.Gestion_Enseignement.Classes.service.EleveService;

import java.util.Date;

@SpringBootApplication
public class GestionEnseignementApplication implements CommandLineRunner {

	private final UtilisateurService utilisateurService;
	private final EnseignantsService enseignantsService;
	private final EleveService eleveService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public GestionEnseignementApplication(UtilisateurService utilisateurService,
										  EnseignantsService enseignantsService,
										  EleveService eleveService,
										  PasswordEncoder passwordEncoder) {
		this.utilisateurService = utilisateurService;
		this.enseignantsService = enseignantsService;
		this.eleveService = eleveService;
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
		Role eleveRole = utilisateurService.ajouter_Role(new Role("Eleve"));

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

		// Création et ajout d'un élève
		Eleve eleve = createEleve("DIALLO", "Aissatou", "adiallo@uasz.sn", "E2024", password);
		eleveService.ajouterEleve(eleve);
		utilisateurService.ajouter_UtilisateurRoles(eleve, eleveRole);
	}

	private Enseignant createEnseignant(String nom, String prenom, String username, String matricule, String password) {
		Enseignant enseignant = new Enseignant();
		enseignant.setNom(nom);
		enseignant.setPrenom(prenom);
		enseignant.setUsername(username);
		enseignant.setPassword(password);
		enseignant.setActive(true);
		enseignant.setMatricule(matricule);
		enseignant.setDateCreation(new Date());
		return enseignant;
	}

	private Eleve createEleve(String nom, String prenom, String username, String code, String password) {
		Eleve eleve = new Eleve();
		eleve.setNom(nom);
		eleve.setPrenom(prenom);
		eleve.setUsername(username);
		eleve.setPassword(password);
		eleve.setCode(code);
		eleve.setDateCreation(new Date());
		return eleve;
	}*/
}
}

