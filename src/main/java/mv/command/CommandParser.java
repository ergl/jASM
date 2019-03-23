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

package mv.command;

import mv.command.commandList.*;

/**
 * Parses user input into debug commands
 *
 * @author Borja
 */
public class CommandParser {

    private static final CommandInterpreter[] commands = {
            new Step(), new Steps(), new Run(), new DebugPush(),
            new DebugPop(), new DebugWrite(), new Reset(), new Quit()
    };

    /**
     * Parses user input and creates a Command object that can be processed by the CPU
     * If the input is wrongly formatted it will ignore the order and it will be parsed as an error
     *
     * @param input user input describing a command
     *
     * @return Parsed command or null
     */
    public static CommandInterpreter parseCommand(String input) {
        for (CommandInterpreter comm : commands) {
            CommandInterpreter tmp = comm.parseComm(input);
            if (tmp != null) {
                return tmp;
            }
        }
        return null;
    }
}
