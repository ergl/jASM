package tp.pr5.mv.ins.instList.summitModifiers;

import tp.pr5.mv.cpu.ExecutionManager;
import tp.pr5.mv.cpu.Memory;
import tp.pr5.mv.cpu.OperandStack;
import tp.pr5.mv.ins.Instruction;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;

/**
 * Realiza la operación IN, apilando un valor input en la cima de la pila.
 * Apila -1 en caso de que no exista input o se haya llegado al final de fichero.
 * 
 * @author Borja
 * @author Chaymae
 */
public class In extends SummitModifiers {

    private int value;

    public In() {
        super("IN");
    }

    @Override
    protected Instruction getInst() {
        return new In();
    }

    @Override
    protected void operation(OperandStack stack, InStrategy in, OutStrategy out) {}

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out) {
        stack.pushValue(in.read());

    }

    public int getValue() {
        return this.value;
    }
}
