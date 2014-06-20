package mv.ins;

import commons.exceptions.*;
import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/**
 * Interfaz que describe la ejecución y parseo de las operaciones de programa.
 *
 * @author Borja
 * @author Chaymae
 */
public interface Instruction {
    abstract void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out)
            throws UnrecoverableException, RecoverableException;

    abstract Instruction parse(String input);
}
