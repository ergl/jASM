package mv.ins.instList.summitModifiers;

import commons.exceptions.RecoverableException;
import commons.exceptions.StackException;

import mv.cpu.OperandStack;
import mv.ins.Instruction;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

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
        } else {
            throw new StackException(this, stack.elements());
        }
    }
}
