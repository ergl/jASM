package tp.pr5.mv.ins.instList.arithmetics;

import tp.pr5.mv.ins.Instruction;

/**
 * Realiza la operació de resta sobre la pila.
 * 
 * @author Borja
 * @author Chaymae
 */
public class Sub extends Arithmetics {

    public Sub() {
        super("SUB");
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return tmp1 - tmp2;
    }

    @Override
    protected Instruction getInst() {
        return new Sub();
    }	
}
