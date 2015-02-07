package commons.exceptions;

/**
 * Error in the ASM execution or any other error that can be corrected by the user.
 *
 * @author Borja
 */
public class RecoverableException extends Exception {

    public RecoverableException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
