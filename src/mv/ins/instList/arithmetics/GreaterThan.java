package mv.ins.instList.arithmetics;

import mv.ins.Instruction;

public class GreaterThan extends Arithmetics {

    public GreaterThan() {
        super("GT");
    }

    @Override
    protected Instruction getInst() {
        return new GreaterThan();
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return (tmp2 > tmp1) ? 1 : 0;
    }
}
