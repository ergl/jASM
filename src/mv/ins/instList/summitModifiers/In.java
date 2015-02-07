package mv.ins.instList.summitModifiers;

import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.ins.Instruction;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/**
 * Pushes onto the stack a value from input (stdin or file).
 * If no value exists, pushes -1
 *
 * @author Borja
 */
public class In extends SummitModifiers {

    public In() {
        super("IN");
    }

    @Override
    protected Instruction getInst() {
        return new In();
    }

    @Override
    protected void operation(OperandStack stack, InStrategy in, OutStrategy out) {
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers) {
        stack.pushValue(in.read());

    }
}
