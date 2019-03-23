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

import commons.exceptions.MemoryException;
import commons.exceptions.OutOfBoundMemoryException;
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

public class Load extends TwoParamInst {

    public Load() {
        super("LOAD"); // TODO: Check NullPointerException
    }

    public Load(int param) {
        super("LOAD", param);
    }

    @Override
    protected Instruction getInst(int param) {
        return new Load(param);
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers)
            throws UnrecoverableException, RecoverableException {

        if (this.param >= 0) {
            if (!memory.isEmpty()) {
                int pos = memory.getMemoryReference(this.param);
                if (pos != -1) {
                    stack.pushValue(memory.loadValue(pos));
                } else {
                    throw new MemoryException("Error: Selected cell is empty");
                }
            } else {
                throw new MemoryException("Error: Memory is empty");
            }
        } else {
            throw new OutOfBoundMemoryException(this.param);
        }
    }
}
