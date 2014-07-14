package tp.pr5.mv.ins.instList.stackModifiers;

import tp.pr5.commons.exceptions.RecoverableException;
import tp.pr5.commons.exceptions.UnrecoverableException;
import tp.pr5.mv.cpu.ExecutionManager;
import tp.pr5.mv.cpu.Memory;
import tp.pr5.mv.cpu.OperandStack;
import tp.pr5.mv.ins.Instruction;
import tp.pr5.mv.ins.instList.TwoParamInst;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;


/**
 * Realiza la operación de push sobre la pila.
 * 
 * @author Borja
 * @author Chaymae
 */
public class Push extends TwoParamInst {

    public Push() {
        super("PUSH");
    }

    public Push(int param) {
        super("PUSH", param);
    }

    /**
     * Crea un objeto de la operación correspondiente.
     * 
     * @return el objeto operación determinado
     */
    @Override
    protected Instruction getInst(int param) {
        return new Push(param);
    }

    /**
     * Método encargado de ejecutar la operación sobre la CPU.
     */
    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out)
            throws UnrecoverableException, RecoverableException {
        
        stack.pushValue(param);
    }
}
