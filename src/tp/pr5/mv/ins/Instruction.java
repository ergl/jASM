package tp.pr5.mv.ins;

import tp.pr5.commons.exceptions.*;
import tp.pr5.mv.cpu.ExecutionManager;
import tp.pr5.mv.cpu.Memory;
import tp.pr5.mv.cpu.OperandStack;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;

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
