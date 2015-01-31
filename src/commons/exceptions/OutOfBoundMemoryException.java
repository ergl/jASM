package tp.pr5.commons.exceptions;

/**
 * Excepción que ocurre cuando se accede a una posición ilegal de la memoria.
 * 
 * @author Borja
 * @author Chaymae
 */
@SuppressWarnings("serial")
public class OutOfBoundMemoryException extends UnrecoverableException {

    public OutOfBoundMemoryException(int illegalReference) {
        super("Acceso a memoria fuera de rango. (Acceso a referencia " + illegalReference + ")");
    }
    
    public String getMessage() {
        return super.getMessage();
    }
}
