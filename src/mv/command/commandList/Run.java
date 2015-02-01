package mv.command.commandList;

import commons.exceptions.RecoverableException;
import mv.command.CommandInterpreter;
import mv.cpu.CPU;

/**
 * Realiza la operación Run sobre la CPU.
 * Realiza la operación step tantas veces como operaciones tenga el programa a ejecutar.
 * 
 * @author Borja
 * @author Chaymae
 */
public class Run extends Step {

    @Override
    public void executeCommand(CPU cpu) throws RecoverableException{
        while(!cpu.isHalted())
            super.executeCommand(cpu);
    }

    @Override
    public CommandInterpreter parseComm(String input) {
        input = input.trim();
        String[] tokens = input.split("\\ ");

        return ((tokens.length == 1) && (tokens[0].equalsIgnoreCase("RUN"))) ? new Run() : null;
    }
}
