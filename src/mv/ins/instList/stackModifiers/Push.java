package mv.ins.instList.stackModifiers;

import commons.exceptions.RecoverableException;
import commons.exceptions.UnrecoverableException;
import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.ins.Instruction;
import mv.ins.instList.TwoParamInst;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

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
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers) throws UnrecoverableException, RecoverableException {

        stack.pushValue(param);
    }
}

