package mv.ins.instList.branches;

import mv.cpu.ExecutionManager;
import mv.ins.Instruction;

/**
 * Conditional jump to X if stack heap is 0 (false)
 *
 * @author Borja
 */
public class Bf extends Branches {

    public Bf() {
        super("BF", -1);
    }

    public Bf(int param) {
        super("BF", param);
    }

    @Override
    protected Instruction getInst(int param) {
        return new Bf(param);
    }

    @Override
    protected void operation(ExecutionManager controlUnit, int value) {
        if (value == 0) {
            controlUnit.setPc(param);
        }
    }
}
