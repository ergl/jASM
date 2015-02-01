package mv.ins.instList.branches;

import mv.cpu.ExecutionManager;
import mv.ins.Instruction;

/**
 * Realiza la operación de salto condicional false.
 * Salta si el valor de la pila es cero.
 * 
 * @author Borja
 * @author Chaymae
 */
public class Bf extends Branches {

    public Bf() {
        super("BF", -1);
    }

    public Bf(int param) {
        super("BF", param);
    }

    /**
     * Crea un objeto de la operación correspondiente.
     * 
     * @return el objeto operación determinado
     */
    @Override
    protected Instruction getInst(int param) {
        return new Bf(param);
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
        if(value == 0)
            controlUnit.setPc(param);
    }
}
