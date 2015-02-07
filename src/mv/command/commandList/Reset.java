package mv.command.commandList;

import commons.exceptions.RecoverableException;
import mv.command.CommandInterpreter;
import mv.cpu.CPU;

/**
 * Resets the CPU
 */
public class Reset extends CommandInterpreter {

    @Override
    public void executeCommand(CPU cpu) throws RecoverableException {
        cpu.reset();
    }

    @Override
    public CommandInterpreter parseComm(String input) {
        input = input.trim();
        String[] tokens = input.split("\\ ");

        if (tokens.length != 1) {
            return null;
        }

        if (tokens[0].equalsIgnoreCase("RESET")) {
            return new Reset();
        }

        return null;
    }
}
