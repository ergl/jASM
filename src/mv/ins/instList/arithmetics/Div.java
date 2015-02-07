package mv.ins.instList.arithmetics;

import commons.exceptions.RecoverableException;
import commons.exceptions.StackException;
import commons.exceptions.UnrecoverableException;

import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.ins.Instruction;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

public class Div extends Arithmetics {

    public Div() {
        super("DIV");
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers)
            throws UnrecoverableException, RecoverableException {

        int tmp1, tmp2;
        if (stack.elements() >= 2) {
            tmp1 = stack.popValue();
            tmp2 = stack.popValue();
            try {
                int tmp3 = tmp2 / tmp1;
                stack.pushValue(tmp3);
            } catch (ArithmeticException ae) {
                stack.pushValue(tmp2);
                stack.pushValue(tmp1);
                throw new RecoverableException("Error: On instruction: " + this + ": " + "Division by zero");
            }
        } else {
            throw new StackException(this, stack.elements());
        }
    }

    @Override
    protected Instruction getInst() {
        return new Div();
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return 0;
    }
}
