package mv.ins.instList.branches;

import mv.cpu.ExecutionManager;
import mv.ins.Instruction;

/**
 * Realiza la operación de salto relativo condicional true.
 * Salta si la cima de la pila es distinta de cero.
 * 
 * @author Borja
 * @author Chaymae
 */
public class RBt extends Branches {

    public RBt() {
        super("RBT", -1);
    }

    public RBt(int param) {
        super("RBT", param);
    }

    /**
     * Método encargado de realizar la operación correspondiente sobre la memoria.
     * 
     *  @return el éxito de la operación
     */
    @Override
    protected Instruction getInst(int param) {
        return new RBt(param);
    }

    /**
     * Crea un objeto de la operación correspondiente.
     */
    @Override
    protected void operation(ExecutionManager controlUnit, int value) {
        if(value != 0)
            controlUnit.incrementPc(param);
    }
}
