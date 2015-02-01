package commons.exceptions;

/**
 * Excepción de programa que finaliza la ejecución del programa
 *  
 * @author Borja
 * @author Chaymae
 */
@SuppressWarnings("serial")
public class UnrecoverableException extends Exception {

    public UnrecoverableException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
