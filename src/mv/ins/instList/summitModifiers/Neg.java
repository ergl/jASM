package tp.pr5.mv.ins.instList.summitModifiers;

import tp.pr5.mv.cpu.OperandStack;
import tp.pr5.mv.ins.Instruction;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;

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
