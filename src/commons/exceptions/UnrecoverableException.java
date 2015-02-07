package commons.exceptions;

/**
 * Any error that causes jASM to exit
 *
 * @author Borja
 */
public class UnrecoverableException extends Exception {

    public UnrecoverableException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
