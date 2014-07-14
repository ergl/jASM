package tp.pr5.mv.ins.instList.summitModifiers;

import tp.pr5.commons.exceptions.*;
import tp.pr5.mv.cpu.OperandStack;
import tp.pr5.mv.ins.Instruction;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;

/**
 * Realiza la operación de Flip sobre la pila.
 * 
 * @author Borja
 * @author Chaymae
 */
public class Flip extends SummitModifiers {

    public Flip() {
        super("FLIP");
    }

    @Override
    protected Instruction getInst() {
        return new Flip();
    }

    @Override
    protected void operation(OperandStack stack, InStrategy in, OutStrategy out) throws RecoverableException {
        if (stack.elements() >= 2) {
            int tmp = stack.popValue();
            int tmp1 = stack.popValue();
            stack.pushValue(tmp);
            stack.pushValue(tmp1);
        }
        else
            throw new StackException(this, stack.elements());
    }
}
