package tp.pr5.mv.ins.instList.misc;

import tp.pr5.commons.exceptions.*;
import tp.pr5.mv.cpu.ExecutionManager;
import tp.pr5.mv.cpu.Memory;
import tp.pr5.mv.cpu.OperandStack;
import tp.pr5.mv.ins.Instruction;
import tp.pr5.mv.ins.instList.OneParamInst;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;

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
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out) throws UnrecoverableException, RecoverableException {
        executionManager.stop();
    }

    /**
     * Crea un objeto de la operación correspondiente.
     * 
     * @return el objeto operación determinado
     */
    @Override
    protected Instruction getInst() {
        return new Halt();
    }
}
