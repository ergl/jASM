package mv.command.commandList;

import commons.exceptions.RecoverableException;
import mv.command.CommandInterpreter;
import mv.cpu.CPU;
import mv.ins.Instruction;

/**
 * Executes a Step in the debugger
 */
public class Step extends CommandInterpreter {

    public Step() {}

    @Override
    public void executeCommand(CPU cpu) throws RecoverableException {
        Instruction nextInstruction = cpu.nextInstruction();

        if (nextInstruction != null) {
            System.out.println(INST_MSG_BEGIN + nextInstruction.toString());
            cpu.step();
            System.out.println(EXEC_END + cpu.toString());
        }
    }

    @Override
    public CommandInterpreter parseComm(String input) {
        input = input.trim();
        String[] tokens = input.split("\\s");
        if (tokens.length != 1) {
            return null;
        }

        if (tokens[0].equalsIgnoreCase("STEP")) {
            return new Step();
        }

        return null;
    }

}
