package mv.ins.instList.arithmetics;

import mv.ins.Instruction;

public class Equals extends Arithmetics {

    public Equals() {
        super("EQ");
    }

    @Override
    protected Instruction getInst() {
        return new Equals();
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return (tmp1 == tmp2) ? 1 : 0;
    }

}
