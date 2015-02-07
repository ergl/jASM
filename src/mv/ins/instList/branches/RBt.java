package mv.ins.instList.branches;

import mv.cpu.ExecutionManager;
import mv.ins.Instruction;

/**
 * Relative jump if the stack heap is != 0
 *
 * @author Borja
 */
public class RBt extends Branches {

    public RBt() {
        super("RBT", -1);
    }

    public RBt(int param) {
        super("RBT", param);
    }

    @Override
    protected Instruction getInst(int param) {
        return new RBt(param);
    }

    @Override
    protected void operation(ExecutionManager controlUnit, int value) {
        if (value != 0) {
            controlUnit.incrementPc(param);
        }
    }
}
