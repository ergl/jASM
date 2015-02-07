package mv.ins.instList.branches;

import mv.cpu.ExecutionManager;
import mv.ins.Instruction;

/**
 * Conditional jump to X if stack heap is != 0 (true)
 *
 * @author Borja
 */
public class Bt extends Branches {

    public Bt() {
        super("BT", -1);
    }

    public Bt(int param) {
        super("BT", param);
    }

    @Override
    protected Instruction getInst(int param) {
        return new Bt(param);
    }

    @Override
    protected void operation(ExecutionManager controlUnit, int value) {
        if (value != 0) {
            controlUnit.setPc(param);
        }
    }
}
