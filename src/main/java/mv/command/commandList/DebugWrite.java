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

package mv.command.commandList;

import commons.Commons;
import mv.command.CommandInterpreter;
import mv.cpu.CPU;

/**
 * Executes a Store Instruction on the CPU
 *
 * @see mv.ins.instList.stackModifiers.Store
 */
public class DebugWrite extends CommandInterpreter {

    private int ref;
    private int val;

    public DebugWrite() {}

    public DebugWrite(int _ref, int _val) {
        this.ref = _ref;
        this.val = _val;
    }

    @Override
    public void executeCommand(CPU cpu) {
        cpu.debugWrite(String.valueOf(this.ref), String.valueOf(this.val));
    }

    @Override
    public CommandInterpreter parseComm(String input) {
        input = input.trim();
        String[] tokens = input.split("\\s");

        if (tokens.length == 3 && tokens[0].equalsIgnoreCase("WRITE")) {
            if (Commons.isInteger(tokens[1]) && Commons.isInteger(tokens[2])) {
                int param1 = Integer.parseInt(tokens[1]);
                int param2 = Integer.parseInt(tokens[2]);
                return new DebugWrite(param1, param2);
            }
        }

        return null;
    }
}
