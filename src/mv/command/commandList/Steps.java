package tp.pr5.mv.command.commandList;

import tp.pr5.commons.Commons;
import tp.pr5.commons.exceptions.RecoverableException;
import tp.pr5.mv.command.CommandInterpreter;
import tp.pr5.mv.cpu.CPU;

/**
 * Realiza la operación Step N sobre la CPU.
 * N es el número de veces que se ejecuta la instrucción Step, de la que deriva.
 * 
 * @author Borja
 * @author Chaymae
 */
public class Steps extends Step {

    private int steps;

    public Steps() {
    }

    public Steps(int steps) {
        this.steps = steps;
    }

    @Override
    public void executeCommand(CPU cpu) throws RecoverableException {
        int i = 0;
        do {
            super.executeCommand(cpu);
            i++;
        }
        while (!cpu.isHalted() && i < this.steps);
    }

    @Override
    public CommandInterpreter parseComm(String input) {
        input = input.trim();
        String[] tokens = input.split("\\ ");

        if(tokens.length == 2 && tokens[0].equalsIgnoreCase("STEP")) {
            if(Commons.isInteger(tokens[1])) {
                int param = Integer.parseInt(tokens[1]);
                return (param >= 1) ? new Steps(param) : null;
            }
            else return null;
        }
        else return null;
    }
}
