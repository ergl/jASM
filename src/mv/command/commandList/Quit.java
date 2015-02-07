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
        String[] tokens = input.split("\\ ");

        if (tokens.length != 1) {
            return null;
        }

        if (tokens[0].equalsIgnoreCase("QUIT")) {
            return new Quit();
        }

        return null;
    }

}
