package tp.pr5.mv.ins.instList.arithmetics;

import tp.pr5.mv.ins.Instruction;

/**
 * Realiza la operación de Or sobre la cima y la subcima de pila.
 * 
 * @author Borja
 * @author Chaymae
 */
public class Or extends Arithmetics {

    public Or() {
        super("OR");
    }

    @Override
    protected Instruction getInst() {
        return new Or();
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return (tmp1 == 0 && tmp2 == 0) ? 0 : 1;
    }
}
