package mv.ins.instList.summitModifiers;

import mv.cpu.OperandStack;
import mv.ins.Instruction;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/**
 * Realiza la operación de Neg sobre la pila.
 * 
 * @author Borja
 * @author Chaymae
 */
public class Neg extends SummitModifiers {

    public Neg() {
        super("NEG");
    }

    @Override
    protected Instruction getInst() {
        return new Neg();
    }

    @Override
    protected void operation(OperandStack stack, InStrategy in, OutStrategy out) {
        int value = stack.popValue();
        stack.pushValue(-value);
    }
}
