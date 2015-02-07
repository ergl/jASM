package mv.ins.instList.summitModifiers;

import mv.cpu.OperandStack;
import mv.ins.Instruction;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

public class Pop extends SummitModifiers {

    public Pop() {
        super("POP");
    }
    
    @Override
    protected Instruction getInst() {
        return new Pop();
    }

    @Override
    protected void operation(OperandStack stack, InStrategy in, OutStrategy out) {
        stack.popValue();
    }
}
