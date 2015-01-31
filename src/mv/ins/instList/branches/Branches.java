package tp.pr5.mv.ins.instList.branches;

import tp.pr5.commons.exceptions.*;
import tp.pr5.mv.cpu.ExecutionManager;
import tp.pr5.mv.cpu.Memory;
import tp.pr5.mv.cpu.OperandStack;
import tp.pr5.mv.ins.Instruction;
import tp.pr5.mv.ins.instList.TwoParamInst;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;

/**
 * Clase abstracta común a todas las operaciones de salto.
 * 
 * @author Borja
 * @author Chaymae
 */
public abstract class Branches extends TwoParamInst {

    public Branches(String orden) {
        super(orden);
    }

    public Branches(String orden, int param) {
        super(orden, param);
    }

    protected abstract Instruction getInst(int param);

    protected abstract void operation(ExecutionManager controlUnit, int value);

    /**
     * Método encargado de ejecutar la operación sobre la CPU.
     * La pila no puede estar vacía.
     */
    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out)
            throws RecoverableException {
        
        if (!stack.isEmpty()) {
            int value = stack.popValue();
            operation(executionManager, value);
        }

        else
            throw new StackException(this, stack.elements());
    }
}
