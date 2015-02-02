package mv.ins.instList.misc;

import commons.exceptions.RecoverableException;
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
 * Realiza la operación de Halt sobre la CPU.
 *
 * @author Borja
 * @author Chaymae
 */
public class Halt extends OneParamInst {

    public Halt() {
        super("HALT");
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers) throws UnrecoverableException, RecoverableException {

        executionManager.stop();
    }

    /**
     * Crea un objeto de la operación correspondiente.
     * <<<<<<< HEAD:src/mv/ins/instList/misc/Halt.java
     * <p>
     * =======
     * <p>
     * >>>>>>> 2fdcfe6... Changed package hierarchy and fixed some code style issues:src/mv/ins/instList/misc/Halt.java
     *
     * @return el objeto operación determinado
     */
    @Override
    protected Instruction getInst() {
        return new Halt();
    }
}
