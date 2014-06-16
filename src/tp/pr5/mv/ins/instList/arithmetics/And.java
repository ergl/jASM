package tp.pr5.mv.ins.instList.arithmetics;

import tp.pr5.mv.ins.Instruction;

/**
 * Realiza la operación de and sobre la pila.
 * Realiza un and sobre la cima y la subcima de la pila.
 * 
 * @author Borja
 * @author Chaymae
 */
public class And extends Arithmetics {

    public And() {
        super("AND");
    }

    @Override
    protected Instruction getInst() {
        return new And();
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return (tmp1 != 0 && tmp2 != 0) ? 1 : 0;
    }
}
