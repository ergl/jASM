package mv.ins.instList.stackModifiers;

import commons.exceptions.OutOfBoundMemoryException;
import commons.exceptions.RecoverableException;
import commons.exceptions.StackException;
import commons.exceptions.UnrecoverableException;

import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.ins.Instruction;
import mv.ins.instList.TwoParamInst;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

public class Store extends TwoParamInst {

    public Store() {
        super("STORE"); // TODO: Check NullPointerException
    }

    public Store(int param) {
        super("STORE", param);
    }

    public Store(int param1, int param2) {
        super("STORE", param1, param2);
    }

    @Override
    protected Instruction getInst(int param) {
        return new Store(param);
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers)
            throws UnrecoverableException, RecoverableException {

        if (this.auxParam != null) {
            if (this.param >= 0) {
                memory.storeValue(this.auxParam, this.param);
            } else {
                throw new OutOfBoundMemoryException(this.param);
            }
        } else {
            if (!stack.isEmpty()) {
                int value = stack.popValue();
                if (this.param >= 0) {
                    memory.storeValue(value, this.param);
                } else {
                    throw new OutOfBoundMemoryException(this.param);
                }
            } else {
                throw new StackException(this, stack.elements());
            }
        }
    }
}
