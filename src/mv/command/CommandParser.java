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
            new DebugPop(), new DebugWrite(), new Reset(),
            new SetBreakpoint(), new Quit()
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
