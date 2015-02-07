package commons.exceptions;

import mv.ins.Instruction;

/**
 * TODO: When?
 */
public class RegisterValueException extends UnrecoverableException {
	 	
	public RegisterValueException(Instruction inst, int n) {
        super("Error: Couldn't execute " + inst + ": Can't have a negative value in register R" + n);
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
