package commons.exceptions;

/**
 * Trying to read or write in an illegal memory address
 *
 * @author Borja
 */
public class OutOfBoundMemoryException extends UnrecoverableException {

    public OutOfBoundMemoryException(int illegalReference) {
        super("Error: Memory address is out of bounds. (Trying to read from / write to address: " + illegalReference + ")");
    }

    public String getMessage() {
        return super.getMessage();
    }
}
