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
import commons.exceptions.RecoverableException;
import mv.command.CommandInterpreter;
import mv.cpu.CPU;

/**
 * Executes N steps in the debugger
 */
public class Steps extends Step {

    private int steps;

    public Steps() {}

    public Steps(int steps) {
        this.steps = steps;
    }

    @Override
    public void executeCommand(CPU cpu) throws RecoverableException {
        int i = 0;
        do {
            super.executeCommand(cpu);
            i++;
        } while (!cpu.isHalted() && i < this.steps);
    }

    @Override
    public CommandInterpreter parseComm(String input) {
        input = input.trim();
        String[] tokens = input.split("\\s");

        if (tokens.length == 2 && tokens[0].equalsIgnoreCase("STEP")) {
            if (Commons.isInteger(tokens[1])) {
                int param = Integer.parseInt(tokens[1]);
                return (param >= 1) ? new Steps(param) : null;
            }
        }

        return null;
    }
}
