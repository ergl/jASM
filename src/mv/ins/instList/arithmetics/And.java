package mv.ins.instList.arithmetics;

import mv.ins.Instruction;

public class And extends Arithmetics {

    public And() {
        super("AND");
    }

    @Override
    protected Instruction getInst() {
        return new And();
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return (tmp1 != 0 && tmp2 != 0) ? 1 : 0;
    }
}
