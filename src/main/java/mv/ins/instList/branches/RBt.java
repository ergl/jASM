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

import mv.cpu.ExecutionManager;
import mv.ins.Instruction;

/**
 * Relative jump if the stack heap is != 0
 *
 * @author Borja
 */
public class RBt extends Branches {

    public RBt() {
        super("RBT", -1);
    }

    public RBt(int param) {
        super("RBT", param);
    }

    @Override
    protected Instruction getInst(int param) {
        return new RBt(param);
    }

    @Override
    protected void operation(ExecutionManager controlUnit, int value) {
        if (value != 0) {
            controlUnit.incrementPc(param);
        }
    }
}
