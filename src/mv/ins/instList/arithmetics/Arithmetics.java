package mv.ins.instList.arithmetics;

import commons.exceptions.*;
import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.ins.Instruction;
import mv.ins.instList.*;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/**
 * Clase abstracta común a todas las operaciones aritméticas en la pila
 *
 * @author Borja
 * @author Chaymae
 */
public abstract class Arithmetics extends OneParamInst {

    public Arithmetics(String orden) {
        super(orden);
    }

    @Override
    protected abstract Instruction getInst();

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out) throws UnrecoverableException, RecoverableException {

        int tmp1, tmp2;
        if (stack.elements() >= 2) {
            tmp1 = stack.popValue();
            tmp2 = stack.popValue();
            stack.pushValue(operation(tmp1, tmp2));
        } else {
            throw new StackException(this, stack.elements());
        }
    }

    protected abstract int operation(int tmp1, int tmp2);
}
