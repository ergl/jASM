package mv.ins.instList.arithmetics;

import commons.exceptions.RecoverableException;
import commons.exceptions.StackException;
import commons.exceptions.UnrecoverableException;

import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.ins.Instruction;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/**
 * Realiza la operación de división sobre la pila.
 *
 * @author Borja
 * @author Chaymae
 */
public class Div extends Arithmetics {

    public Div() {
        super("DIV");
    }

    /**
     * Ejecuta la división sobre la pila.
     * Comprueba que la cima no sea cero.
     */
    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out)
        throws UnrecoverableException, RecoverableException {
        int tmp1, tmp2;
        if (stack.elements() >= 2) {
            tmp1 = stack.popValue();
            tmp2 = stack.popValue();
            try {
                int tmp3 = tmp2 / tmp1;
                stack.pushValue(tmp3);
            } catch (ArithmeticException ae) {
                stack.pushValue(tmp2);
                stack.pushValue(tmp1);
                throw new RecoverableException("Error ejecutando: " + this + ": " + "División por cero");
            }
        } else {
            throw new StackException(this, stack.elements());
        }
    }

    @Override
    protected Instruction getInst() {
        return new Div();
    }

    @Override
    protected int operation(int tmp1, int tmp2) {
        return 0;
    }
}
