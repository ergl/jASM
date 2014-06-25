package mv.ins.instList.summitModifiers;

import commons.exceptions.RecoverableException;
import commons.exceptions.StackException;
import commons.exceptions.UnrecoverableException;

import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.ins.Instruction;
import mv.ins.instList.OneParamInst;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/**
 * Clase abstracta común a todas las operaciones que alteran la cima existente de la pila de alguna manera.
 *
 * @author Borja
 * @author Chaymae
 */
public abstract class SummitModifiers extends OneParamInst {

    public SummitModifiers(String orden) {
        super(orden);
    }

    /**
     * Crea un objeto de la operación correspondiente.
     *
     * @return el objeto operación determinado
     */
    protected abstract Instruction getInst();

    /**
     * Método encargado de realizar la operación correspondiente sobre la memoria.
     *
     * @param in TODO
     * @param out TODO
     *
     * @throws RecoverableException
     */
    protected abstract void operation(OperandStack stack, InStrategy in, OutStrategy out) throws RecoverableException;

    /**
     * Método encargado de ejecutar la operación sobre la CPU.
     */
    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers)
            throws UnrecoverableException, RecoverableException {

        if (!stack.isEmpty()) {
            operation(stack, in, out);
        } else {
            throw new StackException(this, stack.elements());
        }
    }
}

