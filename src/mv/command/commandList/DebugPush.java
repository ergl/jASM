package mv.command.commandList;

import commons.Commons;
import mv.command.CommandInterpreter;
import mv.cpu.CPU;

/**
 * Realiza la operación de Push sobre la CPU.
 * Esta operación es independiente del programa y existe con fines de debug.
 *
 * @author Borja
 * @author Chaymae
 */
public class DebugPush extends CommandInterpreter {
    private int value;

    public DebugPush() {
    }

    public DebugPush(int param) {
        this.value = param;
    }

    @Override
    public void executeCommand(CPU cpu) {
        cpu.debugInstruction(String.valueOf(this.value), null);
    }

    @Override
    public CommandInterpreter parseComm(String input) {
        input = input.trim();
        String[] tokens = input.split("\\ ");

        if (tokens.length != 2) {
            return null;
        }

        if (Commons.isInteger(tokens[1])) {
            int param = Integer.parseInt(tokens[1]);
            if (tokens[0].equalsIgnoreCase("PUSH")) {
                return new DebugPush(param);
            }
        }

        return null;
    }

}
