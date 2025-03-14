package uasz.sn.Gestion_Enseignement.Classes.utils;
import java.security.SecureRandom;
import java.util.Random;

public class PasswordUtil {
    // Définition des caractères autorisés pour le mot de passe
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&*!";

    // Longueur du mot de passe généré
    private static final int PASSWORD_LENGTH = 8;

    // Utilisation de SecureRandom pour plus de sécurité
    private static final Random RANDOM = new SecureRandom();

    // Méthode pour générer un mot de passe aléatoire
    public static String generateRandomPassword() {
        StringBuilder password = new StringBuilder();

        // Génération d'un mot de passe de longueur définie
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }
}
