package commons.exceptions;

/**
 * Excepción de programa que el usuario puede solucionar mediante Debug.
 *
 * @author Borja
 * @author Chaymae
 */
@SuppressWarnings("serial")
public class RecoverableException extends Exception {

    public RecoverableException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
