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
