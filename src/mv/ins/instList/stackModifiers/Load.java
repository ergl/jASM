package mv.ins.instList.stackModifiers;

import commons.exceptions.MemoryException;
import commons.exceptions.OutOfBoundMemoryException;
import commons.exceptions.RecoverableException;
import commons.exceptions.UnrecoverableException;
import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.ins.Instruction;
import mv.ins.instList.TwoParamInst;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

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
                if (pos != -1) {
                    stack.pushValue(memory.loadValue(pos));
                } else {
                    throw new MemoryException("La celda seleccionada está vacía.");
                }
            } else {
                throw new MemoryException("Memoria vacía.");
            }
        } else {
            throw new OutOfBoundMemoryException(this.param);
        }
    }
}
