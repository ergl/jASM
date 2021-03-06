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

import commons.exceptions.*;
import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.ins.Instruction;
import mv.ins.instList.OneParamInst;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/**
 * Loads the summit of the stack on the given reference
 *
 * @author Borja
 */
public class LoadInd extends OneParamInst {

    public LoadInd() {
        super("LOADIND");
    }

    @Override
    protected Instruction getInst() {
        return new LoadInd();
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers)
            throws UnrecoverableException, RecoverableException {

        if (!stack.isEmpty()) {
            int read = stack.popValue();
            if (read >= 0) {
                if (!memory.isEmpty()) {
                    int pos = memory.getMemoryReference(read);
                    if (pos != -1) {
                        stack.pushValue(memory.loadValue(pos));
                    } else {
                        throw new MemoryException("Error: Selected cell is empty");
                    }
                } else {
                    throw new MemoryException("Error: Memory is empty");
                }
            } else {
                stack.pushValue(read);
                throw new OutOfBoundMemoryException(read);
            }
        } else {
            throw new StackException(this, stack.elements());
        }
    }
}
