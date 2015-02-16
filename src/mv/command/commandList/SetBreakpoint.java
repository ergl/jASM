package mv.command.commandList;

import commons.exceptions.RecoverableException;
import mv.command.CommandInterpreter;
import mv.cpu.CPU;

import static commons.Commons.isInteger;

/**
 * @author Borja
 */
public class SetBreakpoint extends CommandInterpreter {

    private int value;

    public SetBreakpoint() {}

    public SetBreakpoint(int param) {
        value = param;
    }

    @Override
    public CommandInterpreter parseComm(String input) {
        input.trim();
        String[] tokens = input.split("\\ ");

        if (tokens.length != 2) {
            return  null;
        }

        if (tokens[0].equalsIgnoreCase("B") && isInteger(tokens[1])) {
            return new SetBreakpoint(Integer.parseInt(tokens[1]));
        }

        return null;
    }

    @Override
    public void executeCommand(CPU cpu) throws RecoverableException {
        cpu.setBreakpoint(value);
    }
}
