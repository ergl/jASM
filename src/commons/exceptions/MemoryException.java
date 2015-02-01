package commons.exceptions;

/**
 * Excepción que ocurre cuando no existen elementos en la memoria o se intenta cargar de una celda vacía.
 * 
 * @author Borja
 * @author Chaymae
 */
@SuppressWarnings("serial")
public class MemoryException extends RecoverableException {

    public MemoryException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
