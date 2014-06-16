package tp.pr5.mv.ins.instList.branches;

import tp.pr5.mv.cpu.ExecutionManager;
import tp.pr5.mv.ins.Instruction;

/**
 * Realiza la operación de salto condicional true.
 * Salta si el valor de la pila es distinto de cero.
 * 
 * @author Borja
 * @author Chaymae
 */
public class Bt extends Branches {

    public Bt() {
        super("BT", -1);
    }

    public Bt(int param) {
        super("BT", param);
    }

    /**
     * Crea un objeto de la operación correspondiente.
     * 
     * @return el objeto operación determinado
     */
    @Override
    protected Instruction getInst(int param) {
        return new Bt(param);
    }

    /**
     * Realiza la operación de salto sobre la unidad de control.
     * 
     * @param controlUnit la unidad de control sobre la que se opera
     * @param value valor al que se quiere saltar
     * @return el éxito de la operación
     */
    @Override
    protected void operation(ExecutionManager controlUnit, int value) {
        if(value != 0)
            controlUnit.setPc(param);
    }
}
