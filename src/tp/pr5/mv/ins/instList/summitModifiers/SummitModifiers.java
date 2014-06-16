package tp.pr5.mv.ins.instList.summitModifiers;

import tp.pr5.commons.exceptions.*;
import tp.pr5.mv.cpu.ExecutionManager;
import tp.pr5.mv.cpu.Memory;
import tp.pr5.mv.cpu.OperandStack;
import tp.pr5.mv.ins.Instruction;
import tp.pr5.mv.ins.instList.OneParamInst;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;

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
     * @param in TODO
     * @param out TODO
     * @throws RecoverableException 
     */
    protected abstract void operation(OperandStack stack, InStrategy in, OutStrategy out) throws RecoverableException;

    /**
     * Método encargado de ejecutar la operación sobre la CPU.
     */
    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out)
            throws UnrecoverableException, RecoverableException {

        if (!stack.isEmpty()) {
            try { 
                operation(stack, in, out); 
            } catch(RecoverableException re) {
                throw re;
            }
        }
        else
            throw new StackException(this, stack.elements());
    }
}
