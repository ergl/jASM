package commons.exceptions;

import mv.ins.Instruction;

@SuppressWarnings("serial")
public class RegisterValueException extends UnrecoverableException {

    public RegisterValueException(Instruction inst, int n) {
        super("Error ejecutando " + inst + ": Valor negativo en registro R" + n);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
