package commons;

/**
 * Clase que proporciona servicios comunes a todos los paquetes.
 *
 * @author Borja
 * @author Chaymae
 */
public class Commons {

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
