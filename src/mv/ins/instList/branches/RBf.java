package mv.ins.instList.branches;

import mv.cpu.ExecutionManager;
import mv.ins.Instruction;

/**
 * Relative jump if the stack heap is 0
 *
 * @author Borja
 */
public class RBf extends Branches {

    public RBf() {
        super("RBF", -1);
    }

    public RBf(int param) {
        super("RBF", param);
    }

    @Override
    protected Instruction getInst(int param) {
        return new RBf(param);
    }

    @Override
    protected void operation(ExecutionManager controlUnit, int value) {
        if (value == 0) {
            controlUnit.incrementPc(param);
        }
    }
}
