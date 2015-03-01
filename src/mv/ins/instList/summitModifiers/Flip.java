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

package mv.ins.instList.summitModifiers;

import commons.exceptions.RecoverableException;
import commons.exceptions.StackException;

import mv.cpu.OperandStack;
import mv.ins.Instruction;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

public class Flip extends SummitModifiers {

    public Flip() {
        super("FLIP");
    }

    @Override
    protected Instruction getInst() {
        return new Flip();
    }

    @Override
    protected void operation(OperandStack stack, InStrategy in, OutStrategy out) throws RecoverableException {
        if (stack.elements() >= 2) {
            int tmp = stack.popValue();
            int tmp1 = stack.popValue();
            stack.pushValue(tmp);
            stack.pushValue(tmp1);
        } else {
            throw new StackException(this, stack.elements());
        }
    }
}
