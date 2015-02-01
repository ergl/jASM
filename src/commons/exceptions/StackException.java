package commons.exceptions;

import mv.ins.Instruction;

/**
 * Excepción que ocurre cuando no existen elementos suficientes en la pila de operandos.
 *
 * @author Borja
 * @author Chaymae
 */
@SuppressWarnings("serial")
public class StackException extends RecoverableException {

    public StackException(Instruction inst, int n) {
        super("Error ejecutando " + inst + ": faltan operandos en la pila (hay " + n + ")");
    }

    public String getMessage() {
        return super.getMessage();
    }
}
