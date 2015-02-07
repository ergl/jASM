package mv.ins.instList.arithmetics;

import mv.ins.Instruction;

public class LessThan extends Arithmetics {

    public LessThan() {
        super("LT");
    }

    @Override
    protected Instruction getInst() {
        return new LessThan();
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return (tmp2 < tmp1) ? 1 : 0;
    }
}
