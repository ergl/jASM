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
 * Executes a Push Instruction on the CPU
 *
 * @see mv.ins.instList.stackModifiers.Push
 */
public class DebugPush extends CommandInterpreter {
    private int value;

    public DebugPush() {}

    public DebugPush(int param) {
        this.value = param;
    }

    @Override
    public void executeCommand(CPU cpu) {
        cpu.debugPush(String.valueOf(this.value));
    }

    @Override
    public CommandInterpreter parseComm(String input) {
        input = input.trim();
        String[] tokens = input.split("\\s");

        if (tokens.length != 2) {
            return null;
        }

        if (Commons.isInteger(tokens[1])) {
            int param = Integer.parseInt(tokens[1]);
            if (tokens[0].equalsIgnoreCase("PUSH")) {
                return new DebugPush(param);
            }
        }

        return null;
    }

}
