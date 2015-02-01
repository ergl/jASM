package mv.ins.instList.arithmetics;

import mv.ins.Instruction;

/**
 * Realiza la operación Less Equal sobre la pila.
 * Apila un 1 en la pila si la anterior cima es menor o igual que la subcima.
 * 
 * @author Borja
 * @author Chaymae
 */
public class LessEqual extends Arithmetics  {

    public LessEqual() {
        super("LE");
    }

    @Override
    protected Instruction getInst() {
        return new LessEqual();
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return (tmp2 <= tmp1) ? 1 : 0;
    }
}
