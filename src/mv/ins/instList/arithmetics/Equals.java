package mv.ins.instList.arithmetics;

import mv.ins.Instruction;

/**
 * Realiza la operación de Equals sobre la pila.
 * Apila un 1 en la pila si la cima anterior es igual a la subcima.
 * 
 * @author Borja
 * @author Chaymae
 */
public class Equals extends Arithmetics {

    public Equals() {
        super("EQ");
    }

    @Override
    protected Instruction getInst() {
        return new Equals();
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return (tmp1 == tmp2) ? 1 : 0;
    }

}
