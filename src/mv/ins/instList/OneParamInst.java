package mv.ins.instList;

import commons.exceptions.RecoverableException;
import commons.exceptions.UnrecoverableException;
import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.ins.Instruction;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/**
 * Describes all instructions that get no parameter (pop, push, halt...)
 *
 * @author Borja
 */
public abstract class OneParamInst implements Instruction {

    protected String order;

    public OneParamInst(String order) {
        this.order = order;
    }

    protected abstract Instruction getInst();

    public abstract void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers)
            throws UnrecoverableException, RecoverableException;

    public Instruction parse(String input) {
        input = input.trim();
        String[] tokens = input.split("\\s");

        if (tokens.length != 1) {
            return null;
        }

        if (tokens[0].equalsIgnoreCase(order)) {
            return getInst();
        }

        return null;
    }

    @Override
    public String toString() {
        return this.order.toUpperCase();
    }
}
