package tp.pr5.mv.ins.instList.stackModifiers;

import tp.pr5.commons.exceptions.*;
import tp.pr5.mv.cpu.ExecutionManager;
import tp.pr5.mv.cpu.Memory;
import tp.pr5.mv.cpu.OperandStack;
import tp.pr5.mv.ins.Instruction;
import tp.pr5.mv.ins.instList.TwoParamInst;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;

/**
 * Realiza la operación de Load sobre la pila.
 * 
 * @author Borja
 * @author Chaymae
 */
public class Load extends TwoParamInst {

    public Load() {
        super("LOAD");
    }

    public Load(int param) {
        super("LOAD", param);
    }

    /**
     * Crea un objeto de la operación correspondiente.
     * 
     * @return el objeto operación determinado
     */
    @Override
    protected Instruction getInst(int param) {
        return new Load(param);
    }

    /**
     * Método encargado de ejecutar la operación sobre la CPU.
     */
    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out)
            throws UnrecoverableException, RecoverableException {
        
        if (this.param >= 0) {
            if (!memory.isEmpty()) {
                int pos = memory.getMemoryReference(this.param);
                if (pos != -1)
                    stack.pushValue(memory.loadValue(pos));
                else
                    throw new MemoryException("La celda seleccionada está vacía.");
            }
            else
                throw new MemoryException("Memoria vacía.");
        }
        else
            throw new OutOfBoundMemoryException(this.param);
    }
}
