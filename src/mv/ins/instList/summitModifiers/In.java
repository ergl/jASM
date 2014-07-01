package mv.ins.instList.summitModifiers;

import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.ins.Instruction;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/**
 * Realiza la operación IN, apilando un valor input en la cima de la pila.
 * Apila -1 en caso de que no exista input o se haya llegado al final de fichero.
 *
 * @author Borja
 * @author Chaymae
 */
public class In extends SummitModifiers {

    public In() {
        super("IN");
    }

    @Override
    protected Instruction getInst() {
        return new In();
    }

    @Override
    protected void operation(OperandStack stack, InStrategy in, OutStrategy out) {
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers) {
        stack.pushValue(in.read());

    }
}
