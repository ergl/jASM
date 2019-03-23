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

import mv.command.CommandInterpreter;
import mv.cpu.CPU;

/**
 * Shuts down the debugger.
 */
public class Quit extends CommandInterpreter {

    public Quit() {}

    @Override
    public void executeCommand(CPU cpu) {
        cpu.stop();
    }

    @Override
    public CommandInterpreter parseComm(String input) {
        input = input.trim();
        String[] tokens = input.split("\\s");

        if (tokens.length != 1) {
            return null;
        }

        if (tokens[0].equalsIgnoreCase("QUIT")) {
            return new Quit();
        }

        return null;
    }

}
