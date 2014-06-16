package tp.pr5.mv.ins.instList.arithmetics;

import tp.pr5.mv.ins.Instruction;

/**
 * Realiza la operación de multipicación sobre la pila.
 * 
 * @author Borja
 * @author Chaymae
 */
public class Mul extends Arithmetics {

    public Mul() {
        super("MUL");
    }

    @Override
    protected Instruction getInst() {
        return new Mul();
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return tmp1 * tmp2;
    }
}
