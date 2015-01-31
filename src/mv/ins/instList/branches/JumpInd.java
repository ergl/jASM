package tp.pr5.mv.ins.instList.branches;

import tp.pr5.commons.exceptions.*;
import tp.pr5.mv.cpu.ExecutionManager;
import tp.pr5.mv.cpu.Memory;
import tp.pr5.mv.cpu.OperandStack;
import tp.pr5.mv.ins.Instruction;
import tp.pr5.mv.ins.instList.OneParamInst;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;

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
        }
        else
            throw new StackException(this, stack.elements());
    }
}
