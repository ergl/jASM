package tp.pr5.mv.ins.instList.summitModifiers;

import tp.pr5.mv.cpu.OperandStack;
import tp.pr5.mv.ins.Instruction;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;

/**
 * Realiza la operación de Dup sobre la pila.
 * 
 * @author Bora
 * @author Chaymae
 */
public class Dup extends SummitModifiers {

    public Dup() {
        super("DUP");
    }

    @Override
    protected Instruction getInst() {
        return new Dup();
    }

    @Override
    protected void operation(OperandStack stack, InStrategy in, OutStrategy out) {
        int tmp = stack.popValue();
        stack.pushValue(tmp);
        stack.pushValue(tmp);
    }
}
