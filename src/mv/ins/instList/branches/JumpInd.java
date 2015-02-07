package mv.ins.instList.branches;

import commons.exceptions.RecoverableException;
import commons.exceptions.StackException;
import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.ins.Instruction;
import mv.ins.instList.OneParamInst;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/**
 * Inconditonal Jump to the stack heap.
 * If the stack heap is an impossible value to jump to, pushes again to the stack
 *
 * @author Borja
 */
public class JumpInd extends OneParamInst {

    public JumpInd() {
        super("JUMPIND");
    }

    @Override
    protected Instruction getInst() {
        return new JumpInd();
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers)
            throws RecoverableException {

        if (!stack.isEmpty()) {
            int value = stack.popValue();
            executionManager.setPc(value);
        } else {
            throw new StackException(this, stack.elements());
            // TODO: push to the stack again?
        }
    }
}
