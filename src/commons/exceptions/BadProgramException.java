package commons.exceptions;

/**
 * Excepción que ocurre cuando el programa ASM contiene sintaxis incorrecto.
 * 
 * @author Borja
 * @author Chaymae
 */
@SuppressWarnings("serial")
public class BadProgramException extends UnrecoverableException {

    public BadProgramException(String badInstruction) {
        super("Error en el programa. Línea: " + badInstruction);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
