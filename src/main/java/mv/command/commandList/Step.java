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

import commons.exceptions.RecoverableException;
import mv.command.CommandInterpreter;
import mv.cpu.CPU;
import mv.ins.Instruction;

/**
 * Executes a Step in the debugger
 */
public class Step extends CommandInterpreter {

    public Step() {}

    @Override
    public void executeCommand(CPU cpu) throws RecoverableException {
        Instruction nextInstruction = cpu.nextInstruction();

        if (nextInstruction != null) {
            System.out.println(INST_MSG_BEGIN + nextInstruction.toString());
            cpu.step();
            System.out.println(EXEC_END + cpu.toString());
        }
    }

    @Override
    public CommandInterpreter parseComm(String input) {
        input = input.trim();
        String[] tokens = input.split("\\s");
        if (tokens.length != 1) {
            return null;
        }

        if (tokens[0].equalsIgnoreCase("STEP")) {
            return new Step();
        }

        return null;
    }

}
