package mv.ins.instList.arithmetics;

import commons.exceptions.*;
import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.ins.Instruction;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/**
 * Realiza la operación de Not sobre la cima de la pila.
 *
 * @author Borja
 * @author Chaymae
 */
public class Not extends Arithmetics {

    public Not() {
        super("NOT");
    }

    @Override
    protected Instruction getInst() {
        return new Not();
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return 0;
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out) throws UnrecoverableException, RecoverableException {
        if (!stack.isEmpty()) {
            int tmp = stack.popValue();

            if (tmp != 0) {
                stack.pushValue(0);
            } else {
                stack.pushValue(1);
            }
        } else {
            throw new StackException(this, stack.elements());
        }
    }
}
