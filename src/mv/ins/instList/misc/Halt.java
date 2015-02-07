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
 * Instruction that shuts down the CPU
 *
 * @author Borja
 */
public class Halt extends OneParamInst {

    public Halt() {
        super("HALT");
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers)
            throws UnrecoverableException, RecoverableException {

        executionManager.stop();
    }

    @Override
    protected Instruction getInst() {
        return new Halt();
    }
}
