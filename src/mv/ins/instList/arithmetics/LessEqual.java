package mv.ins.instList.arithmetics;

import mv.ins.Instruction;

public class LessEqual extends Arithmetics {

    public LessEqual() {
        super("LE");
    }

    @Override
    protected Instruction getInst() {
        return new LessEqual();
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return (tmp2 <= tmp1) ? 1 : 0;
    }
}
