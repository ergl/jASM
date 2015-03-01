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

import mv.ins.Instruction;
import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

public class Jump extends Branches {

    public Jump() {
        super("JUMP", -1);
    }

    public Jump(int param) {
        super("JUMP", param);
    }

    @Override
    protected Instruction getInst(int param) {
        return new Jump(param);
    }

    @Override
    protected void operation(ExecutionManager controlUnit, int value) {
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers) {
        executionManager.setPc(this.param);
    }
}
