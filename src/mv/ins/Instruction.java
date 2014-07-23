package mv.ins;

import commons.exceptions.RecoverableException;
import commons.exceptions.UnrecoverableException;
import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/**
 * Interfaz que describe la ejecución y parseo de las operaciones de programa.
 *
 * @author Borja
 * @author Chaymae
 */
public interface Instruction {
    abstract void execute(ExecutionManager executionManager, Memory memory,
    		OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers)
            throws UnrecoverableException, RecoverableException;

    abstract Instruction parse(String input);
}
