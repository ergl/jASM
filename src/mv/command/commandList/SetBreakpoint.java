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

import static commons.Commons.isInteger;

/**
 * @author Borja
 */
public class SetBreakpoint extends CommandInterpreter {

    private int value;

    public SetBreakpoint() {}

    public SetBreakpoint(int param) {
        value = param;
    }

    @Override
    public CommandInterpreter parseComm(String input) {
        input.trim();
        String[] tokens = input.split("\\s");

        if (tokens.length != 2) {
            return  null;
        }

        if (tokens[0].equalsIgnoreCase("B") && isInteger(tokens[1])) {
            return new SetBreakpoint(Integer.parseInt(tokens[1]));
        }

        return null;
    }

    @Override
    public void executeCommand(CPU cpu) throws RecoverableException {
        cpu.setBreakpoint(value);
    }
}
