/*
 * This file is part of jASM.
 *
 * jASM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jASM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jASM.  If not, see <http://www.gnu.org/licenses/>.
 */

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

/**
 * Moves the summit of the stack to the given register (deletes the element from the stack)
 */
public class Move extends TwoParamInst {

    public Move() {
        super("MOVE"); // TODO: Check NullPointerException
    }

    public Move(String register, int number) {
        super("MOVE", register, number);
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers)
            throws UnrecoverableException, RecoverableException {

        if (stack.isEmpty()) {
            throw new StackException(this, stack.elements());
        }

        registers.store(param, stack.popValue());
    }

    @Override
    public Instruction parse(String input) {
        input = input.trim();
        String[] tokens = input.split("\\s");

        if (tokens.length != 2) {
            return null;
        }

        char[] register = tokens[1].toCharArray();

        if (register.length <= 1 || !Character.isDigit(register[1])) {
            return null;
        }

        int param = Character.getNumericValue(register[1]);
        if (tokens[0].equalsIgnoreCase(order)) {
            return getInst(param);
        }

        return null;
    }

    @Override
    protected Instruction getInst(int param) {
        return new Move("R", param);
    }
}
