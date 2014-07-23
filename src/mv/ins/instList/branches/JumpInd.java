package mv.ins.instList.branches;

import commons.exceptions.RecoverableException;
import commons.exceptions.StackException;
import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.ins.Instruction;
import mv.ins.instList.OneParamInst;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/**
 * Realiza la operación de salto dependiendo del valor de la cima de la pila.
 * En caso de que sea imposible el salto, vuelve a apilar el valor en la pila.
 *
 * @author Borja
 * @author Chaymae
 */
public class JumpInd extends OneParamInst {

    public JumpInd() {
        super("JUMPIND");
    }

    @Override
    protected Instruction getInst() {
        return new JumpInd();
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out) throws RecoverableException {
        if (!stack.isEmpty()) {
            int value = stack.popValue();
            executionManager.setPc(value);
        } else {
            throw new StackException(this, stack.elements());
        }
    }
}
