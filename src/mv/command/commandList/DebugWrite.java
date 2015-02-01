package mv.command.commandList;

import commons.Commons;
import mv.command.CommandInterpreter;
import mv.cpu.CPU;

/**
 * Realiza la operación de Write sobre la CPU.
 * Esta operación es independiente del programa y existe con fines de debug.
 * 
 * @author Borja
 * @author Chaymae
 */
public class DebugWrite extends CommandInterpreter {
    private int ref;
    private int val;

    public DebugWrite() {}

    public DebugWrite(int _ref, int _val) {
        this.ref = _ref;
        this.val = _val;
    }

    @Override
    public void executeCommand(CPU cpu) {
        cpu.debugInstruction(String.valueOf(this.ref), String.valueOf(this.val));
    }

    @Override
    public CommandInterpreter parseComm(String input) {
        input = input.trim();
        String[] tokens = input.split("\\ ");

        if (tokens.length == 3 && tokens[0].equalsIgnoreCase("WRITE")) {
            if (Commons.isInteger(tokens[1]) && Commons.isInteger(tokens[2])) {
                int param1 = Integer.parseInt(tokens[1]);
                int param2 = Integer.parseInt(tokens[2]);
                return new DebugWrite(param1, param2);
            }
        }

        return null;
    }
}
