package tp.pr5.mv.ins.instList.summitModifiers;

import tp.pr5.mv.cpu.OperandStack;
import tp.pr5.mv.ins.Instruction;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;

/**
 * Realiza la operación OUT
 * Muestra por consola o escribe a fichero el valor indicado por la cima de la pila
 * 
 * @author Borja
 * @author Chaymae
 */
public class Out extends SummitModifiers {

    public Out() {
        super("OUT");
    }

    @Override
    protected Instruction getInst() {
        return new Out();
    }

    @Override
    protected void operation(OperandStack stack, InStrategy in, OutStrategy out) {
        int value = stack.popValue();
        if(value <= 0)
            value = value * -1;
        out.write((char) value);
    }
}
