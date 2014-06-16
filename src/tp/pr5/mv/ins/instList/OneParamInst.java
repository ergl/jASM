package tp.pr5.mv.ins.instList;

import tp.pr5.commons.exceptions.RecoverableException;
import tp.pr5.commons.exceptions.UnrecoverableException;
import tp.pr5.mv.cpu.ExecutionManager;
import tp.pr5.mv.cpu.Memory;
import tp.pr5.mv.cpu.OperandStack;
import tp.pr5.mv.ins.Instruction;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;

/**
 * Clase abstracta común a todas las instrucciones que no necesitan parámetro por parte del usuario. 
 * 
 * @author Borja
 * @author Chaymae
 */
public abstract class OneParamInst implements Instruction {
    
    protected String orden;

    public OneParamInst(String orden){
        this.orden = orden;
    }

    /**
     * Crea un objeto de la operación correspondiente.
     * 
     * @return el objeto operación determinado
     */
    protected abstract Instruction getInst();

    /**
     * Método encargado de ejecutar la operación sobre la CPU.
     */
    public abstract void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out)
            throws UnrecoverableException, RecoverableException;

    /**
     * Analiza el input del usuario.
     * No necesita de parámetro.
     * 
     * @return la instrucción defindia por el usuario. Si la instrucción no existe devolverá una instrucción nula.
     */
    public Instruction parse(String input) {
        input = input.trim();
        String[] tokens = input.split("\\ ");

        if (tokens.length != 1)
            return null;

        if(tokens[0].equalsIgnoreCase(orden))
            return getInst();

        return null;
    }

    /**
     * Devuelve una versión imprimible de la operación.
     */
    @Override
    public String toString() {
        return this.orden.toUpperCase();
    }
}
