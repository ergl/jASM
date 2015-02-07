package commons.exceptions;

import mv.ins.Instruction;

/**
 * TODO: When?
 */
public class RegisterValueException extends UnrecoverableException {
	 	
	public RegisterValueException(Instruction inst, int n) {
        super("Error: Couldn't execute " + inst + ": Valor negativo en registro R" + n);
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
