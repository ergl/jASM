package commons.exceptions;

import mv.ins.Instruction;

/**
 * When there aren't enough elements if the stack to perform an operation
 *
 * @author Borja
 */
public class StackException extends RecoverableException {

    public StackException(Instruction inst, int n) {
        super("Error: Execution " + inst + ": not enough elements in the stack (" + n + " elements)");
    }

    public String getMessage() {
        return super.getMessage();
    }
}
