package mv.ins.instList.summitModifiers;

import commons.exceptions.RecoverableException;
import commons.exceptions.StackException;
import commons.exceptions.UnrecoverableException;

import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.ins.Instruction;
import mv.ins.instList.OneParamInst;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

public abstract class SummitModifiers extends OneParamInst {

    public SummitModifiers(String order) {
        super(order);
    }

    protected abstract Instruction getInst();

    protected abstract void operation(OperandStack stack, InStrategy in, OutStrategy out) throws RecoverableException;

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers)
            throws UnrecoverableException, RecoverableException {

        if (!stack.isEmpty()) {
            operation(stack, in, out);
        } else {
            throw new StackException(this, stack.elements());
        }
    }
}
