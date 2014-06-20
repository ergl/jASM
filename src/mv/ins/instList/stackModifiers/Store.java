package mv.ins.instList.stackModifiers;

import commons.exceptions.*;
import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.ins.Instruction;
import mv.ins.instList.TwoParamInst;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

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
            if (this.param >= 0) {
                memory.storeValue(this.auxParam, this.param);
            } else {
                throw new OutOfBoundMemoryException(this.param);
            }
        } else {
            if (!stack.isEmpty()) {
                int value = stack.popValue();
                if (this.param >= 0) {
                    memory.storeValue(value, this.param);
                } else {
                    throw new OutOfBoundMemoryException(this.param);
                }
            } else {
                throw new StackException(this, stack.elements());
            }
        }
    }
}
