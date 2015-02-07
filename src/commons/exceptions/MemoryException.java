package commons.exceptions;

/**
 * Trying to read from an empty memory cell, or if the memory is empty.
 *
 * @author Borja
 */
public class MemoryException extends RecoverableException {

    public MemoryException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
