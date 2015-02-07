package mv.ins.instList.arithmetics;

import mv.ins.Instruction;

public class Or extends Arithmetics {

    public Or() {
        super("OR");
    }

    @Override
    protected Instruction getInst() {
        return new Or();
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return (tmp1 == 0 && tmp2 == 0) ? 0 : 1;
    }
}
