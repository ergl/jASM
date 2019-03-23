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

import commons.exceptions.OutOfBoundMemoryException;
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

/**
 * Stores the value of the summit of the stack on the reference given by the second to first element on the stack
 *
 * @author Borja
 */
public class StoreInd extends OneParamInst {

    public StoreInd() {
        super("STOREIND");
    }

    @Override
    protected Instruction getInst() {
        return new StoreInd();
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers)
            throws UnrecoverableException, RecoverableException {

        if (stack.elements() >= 2) {
            int value = stack.popValue();
            int ref = stack.popValue();
            if (ref >= 0) {
                memory.storeValue(value, ref);
                stack.pushValue(ref);
            } else {
                stack.pushValue(ref);
                stack.pushValue(value);
                throw new OutOfBoundMemoryException(ref);
            }
        } else {
            throw new StackException(this, stack.elements());
        }
    }
}
