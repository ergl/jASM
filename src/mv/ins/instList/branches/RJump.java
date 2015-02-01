package mv.ins.instList.branches;

import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.ins.Instruction;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/** 
 * Realiza la operación de salto relativo incondicional.
 * 
 * @author Borja
 * @author Chaymae
 */
public class RJump extends Branches {

    public RJump() {
        super("RJUMP", -1);
    }

    public RJump(int param) {
        super("RJUMP", param);
    }

    /**
     * Crea un objeto de la operación correspondiente.
     * 
     * @return el objeto operación determinado
     */
    @Override
    protected Instruction getInst(int param) {
        return new RJump(param);
    }

    @Override
    protected void operation(ExecutionManager controlUnit, int value) {}

    /**
     * Método encargado de ejecutar la operación sobre la CPU.
     */
    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out){
        executionManager.incrementPc(this.param);
    }
}
