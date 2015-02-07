package mv.ins.instList.arithmetics;

import mv.ins.Instruction;

public class Sub extends Arithmetics {

    public Sub() {
        super("SUB");
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return tmp1 - tmp2;
    }

    @Override
    protected Instruction getInst() {
        return new Sub();
    }
}
