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
 * Unconditional Jump to the stack heap.
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
