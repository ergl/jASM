package tp.pr5.mv.ins.instList.arithmetics;

import tp.pr5.commons.exceptions.*;
import tp.pr5.mv.cpu.ExecutionManager;
import tp.pr5.mv.cpu.Memory;
import tp.pr5.mv.cpu.OperandStack;
import tp.pr5.mv.ins.Instruction;
import tp.pr5.mv.ins.instList.*;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;

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
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out)
            throws UnrecoverableException, RecoverableException {
        
        int tmp1, tmp2;
        if (stack.elements() >= 2) {
            tmp1 = stack.popValue();
            tmp2 = stack.popValue();
            stack.pushValue(operation(tmp1, tmp2));
        }
        else
            throw new StackException(this, stack.elements());
    }

    protected abstract int operation(int tmp1, int tmp2);
}
