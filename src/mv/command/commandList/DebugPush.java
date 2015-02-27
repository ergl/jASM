package mv.command.commandList;

import commons.Commons;
import mv.command.CommandInterpreter;
import mv.cpu.CPU;

/**
 * Executes a Push Instruction on the CPU
 *
 * @see mv.ins.instList.stackModifiers.Push
 */
public class DebugPush extends CommandInterpreter {
    private int value;

    public DebugPush() {}

    public DebugPush(int param) {
        this.value = param;
    }

    @Override
    public void executeCommand(CPU cpu) {
        cpu.debugPush(String.valueOf(this.value));
    }

    @Override
    public CommandInterpreter parseComm(String input) {
        input = input.trim();
        String[] tokens = input.split("\\s");

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
