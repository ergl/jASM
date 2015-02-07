package mv.ins.instList.arithmetics;

import mv.ins.Instruction;

public class Add extends Arithmetics {

    public Add() {
        super("ADD");
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return tmp1 + tmp2;
    }

    @Override
    protected Instruction getInst() {
        return new Add();
    }
}
