package mv.ins.instList.branches;

import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.ins.Instruction;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/**
 * Relative jump to X
 *
 * @author Borja
 */
public class RJump extends Branches {

    public RJump() {
        super("RJUMP", -1);
    }

    public RJump(int param) {
        super("RJUMP", param);
    }

    @Override
    protected Instruction getInst(int param) {
        return new RJump(param);
    }

    @Override
    protected void operation(ExecutionManager controlUnit, int value) {
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers) {
        executionManager.incrementPc(this.param);
    }
}
