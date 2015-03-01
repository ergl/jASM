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

package mv.ins.instList.branches;

import commons.Commons;
import commons.exceptions.RecoverableException;
import commons.exceptions.RegisterValueException;
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
 * Goes to the param instruction while the value in register is greater than zero.
 *
 * When the value gets to zero, it continues the execution of the program
 * If the value in that register drops below zero, raises a RegisterValueException.
 */
public class Loopdec extends TwoParamInst {

    public Loopdec() {
        super("LOOPDEC");
    }

    public Loopdec(String register, int param, int param1) { // TODO: Check npe, why use the param1?
        super("LOOPDEC", register, param, param1);
        this.param = param;
    }

    @Override
    public Instruction parse(String input) {

        input = input.trim();
        String[] tokens = input.split("\\s");

        if (tokens.length != 3) {
            return null;
        }

        char[] register = tokens[1].toCharArray();
        if (!Character.isDigit(register[1])) {
            return null;
        }

        int param = Character.getNumericValue(register[1]);
        if (!Commons.isInteger(tokens[2])) {
            return null;
        }

        int auxparam = Integer.parseInt(tokens[2]);
        if (tokens[0].equalsIgnoreCase(order)) {
            return new Loopdec("R", param, auxparam);
        }

        return null;
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers)
            throws UnrecoverableException, RecoverableException {

        registers.store(param, registers.load(param) - 1);

        if (registers.load(param) < 0) {
            throw new RegisterValueException(this, param);
        }

        if (registers.load(param) > 0) {
            executionManager.setPc(auxParam);
        }
    }

    @Override
    protected Instruction getInst(int param) {
        return null;
    }
}
