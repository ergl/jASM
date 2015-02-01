package mv.ins.instList.branches;

import mv.cpu.ExecutionManager;
import mv.ins.Instruction;

/**
 * Realiza la operación de salto relativo condicional false.
 * Salta si la cima de la pila es cero.
 * 
 * @author Borja
 * @author Chaymae
 */
public class RBf extends Branches {

    public RBf() {
        super("RBF", -1);
    }

    public RBf(int param) {
        super("RBF", param);
    }

    /**
     * Crea un objeto de la operación correspondiente.
     * 
     * @return el objeto operación determinado
     */
    @Override
    protected Instruction getInst(int param) {
        return new RBf(param);
    }

    /**
     * Método encargado de realizar la operación correspondiente sobre la memoria.
     */
    @Override
    protected void operation(ExecutionManager controlUnit, int value) {
        if(value == 0)
            controlUnit.incrementPc(param);
    }
}
