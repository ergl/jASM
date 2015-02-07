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
        String[] tokens = input.split("\\ ");

        if (tokens.length == 2 && tokens[0].equalsIgnoreCase("STEP")) {
            if (Commons.isInteger(tokens[1])) {
                int param = Integer.parseInt(tokens[1]);
                return (param >= 1) ? new Steps(param) : null;
            }
        }

        return null;
    }
}
