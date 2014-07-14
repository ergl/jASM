package tp.pr5.mv.ins.instList.arithmetics;

import tp.pr5.mv.ins.Instruction;

/**
 * Realiza la operación de Less Than sobre la pila.
 * Apila un 1 en la cima de la pila si la cima anterior es menor que la subcima.
 * 
 * @author Borja
 * @author Chaymae
 */
public class LessThan extends Arithmetics  {

    public LessThan() {
        super("LT");
    }

    @Override
    protected Instruction getInst() {
        return new LessThan();
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return (tmp2 < tmp1) ? 1 : 0;
    }
}
