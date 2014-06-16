package tp.pr5.mv.ins.instList.stackModifiers;

import tp.pr5.commons.exceptions.*;
import tp.pr5.mv.cpu.ExecutionManager;
import tp.pr5.mv.cpu.Memory;
import tp.pr5.mv.cpu.OperandStack;
import tp.pr5.mv.ins.Instruction;
import tp.pr5.mv.ins.instList.TwoParamInst;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;

/**
 * Realiza la operación de Store sobre la memoria.
 * 
 * @author Borja
 * @author Chaymae
 */
public class Store extends TwoParamInst {

    public Store() {
        super("STORE");
    }

    public Store(int param) {
        super("STORE", param);
    }

    public Store(int param1, int param2) {
        super("STORE", param1, param2);
    }

    /**
     * Crea un objeto de la operación correspondiente.
     * 
     * @return el objeto operación determinado
     */
    @Override
    protected Instruction getInst(int param) {
        return new Store(param);
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out)
            throws UnrecoverableException, RecoverableException {
        
        if (this.auxParam != null) {
            if (this.param >= 0)
                memory.storeValue(this.auxParam, this.param);
            else 
                throw new OutOfBoundMemoryException(this.param);
        }
        else {		
            if (!stack.isEmpty()) {
                int value = stack.popValue();
                if (this.param >= 0)
                    memory.storeValue(value, this.param);
                else throw new OutOfBoundMemoryException(this.param);
            }
            else
                throw new StackException(this, stack.elements());
        }	
    }
}
