package mv.command.commandList;

import mv.command.CommandInterpreter;
import mv.cpu.CPU;

/**
 * Executes a Pop Instruction on the CPU
 *
 * @see mv.ins.instList.summitModifiers.Pop
 */
public class DebugPop extends CommandInterpreter {

    public DebugPop() {}

    @Override
    public void executeCommand(CPU cpu) {
        cpu.debugInstruction(null, null);
    }

    @Override
    public CommandInterpreter parseComm(String input) {
        input = input.trim();
        String[] tokens = input.split("\\ ");

        if (tokens.length != 1) {
            return null;
        }

        if (tokens[0].equalsIgnoreCase("POP")) {
            return new DebugPop();
        }

        return null;
    }

    @Override
    public String toString() {
        return "POP";
    }
}
