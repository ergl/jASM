package tp.pr5.mv.ins.instList.summitModifiers;

import tp.pr5.mv.cpu.OperandStack;
import tp.pr5.mv.ins.Instruction;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;

/**
 * Realiza la operación pop sobre la pila.
 * 
 * @author Borja
 * @author Chaymae
 */
public class Pop extends SummitModifiers {

    public Pop() {
        super("POP");
    }

    /**
     * Crea un objeto de la operación correspondiente.
     * 
     * @return el objeto operación determinado
     */
    @Override
    protected Instruction getInst() {
        return new Pop();
    }

    @Override
    protected void operation(OperandStack stack, InStrategy in, OutStrategy out) {
        stack.popValue();
    }
}
