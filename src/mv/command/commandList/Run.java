package mv.command.commandList;

import commons.exceptions.RecoverableException;
import mv.command.CommandInterpreter;
import mv.cpu.CPU;

/**
 * Runs the target ASM program
 */
public class Run extends Step {

    @Override
    public void executeCommand(CPU cpu) throws RecoverableException {
        while (!cpu.isHalted())
            super.executeCommand(cpu);
    }

    @Override
    public CommandInterpreter parseComm(String input) {
        input = input.trim();
        String[] tokens = input.split("\\ ");

        if ((tokens.length == 1) && (tokens[0].equalsIgnoreCase("RUN"))) {
            return new Run();
        } else {
            return null;
        }
    }
}
