package mv.ins.instList.stackModifiers;

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

public class Move extends TwoParamInst {

    public Move() {
        super("MOVE");
    }

    public Move(String register, int number) {
        super("MOVE", register, number);
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory,
                        OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers)
            throws UnrecoverableException, RecoverableException {

        if(stack.isEmpty())
            throw new StackException(this, stack.elements());

        registers.store(param, stack.popValue());
    }

    @Override
    public Instruction parse(String input) {
        input = input.trim();
        String[] tokens = input.split("\\s");

        if (tokens.length != 2)
            return null;

        char[] register = tokens[1].toCharArray();

        if(!Character.isDigit(register[1]))
            return null;

        int param = Character.getNumericValue(register[1]);
        if (tokens[0].equalsIgnoreCase(orden))
            return getInst(param);

        return null;
    }

    @Override
    protected Instruction getInst(int param) {
        return new Move("R", param);
    }
}
