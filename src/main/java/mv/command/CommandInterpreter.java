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

import commons.exceptions.RecoverableException;
import mv.cpu.CPU;

/**
 * Sets up the debug environment and describes common commands methods
 *
 * @author Borja
 */
public abstract class CommandInterpreter {

    protected static final String EXEC_END = "CPU state after step: ";
    protected static final String INST_MSG_BEGIN = "Executing: ";

    protected static CPU cpu;

    // Which CPU are we debugging?
    public static void configure(CPU cpu) {
        CommandInterpreter.cpu = cpu;
    }

    /**
     * Parses user input and returns the appropriate Command object
     *
     * @param input user input
     *
     * @return Appropriate Command object
     */
    public abstract CommandInterpreter parseComm(String input);

    /**
     * Executes the command
     *
     * @param cpu CPU in which to execute the command
     *
     * @throws RecoverableException TODO
     */
    public abstract void executeCommand(CPU cpu) throws RecoverableException;

    /**
     * Is the CPU off?
     *
     * @return CPU state
     */
    public static boolean isQuit() {
        return cpu.isHalted();
    }
}
