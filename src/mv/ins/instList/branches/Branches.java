package mv.ins.instList.branches;

import commons.exceptions.RecoverableException;
import commons.exceptions.StackException;
import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.ins.Instruction;
import mv.ins.instList.TwoParamInst;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

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
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers)
            throws RecoverableException {

        if (!stack.isEmpty()) {
            int value = stack.popValue();
            operation(executionManager, value);
        } else {
            throw new StackException(this, stack.elements());
        }
    }
}
