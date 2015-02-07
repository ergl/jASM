package commons.exceptions;

/**
 * Syntax error in ASM source code
 *
 * @author Borja
 */
public class BadProgramException extends UnrecoverableException {

    public BadProgramException(String badInstruction) {
        super("Error: Syntax error. In line: " + badInstruction);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
