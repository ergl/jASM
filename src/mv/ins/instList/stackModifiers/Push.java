package mv.ins.instList.stackModifiers;

import commons.exceptions.RecoverableException;
import commons.exceptions.UnrecoverableException;
import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.ins.Instruction;
import mv.ins.instList.TwoParamInst;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

public class Push extends TwoParamInst {

    public Push() {
        super("PUSH"); // TODO: Check NullPointerException
    }

    public Push(int param) {
        super("PUSH", param);
    }

    @Override
    protected Instruction getInst(int param) {
        return new Push(param);
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers)
            throws UnrecoverableException, RecoverableException {

        stack.pushValue(param);
    }
}

