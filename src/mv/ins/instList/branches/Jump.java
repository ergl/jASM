package mv.ins.instList.branches;

import mv.ins.Instruction;
import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/**
 * Realiza la operación de salto incondicional.
 *
 * @author Borja
 * @author Chaymae
 */
public class Jump extends Branches {

    public Jump() {
        super("JUMP");
    }

    public Jump(int param) {
        super("JUMP", param);
    }

    /**
     * Crea un objeto de la operación correspondiente.
     *
     * @return el objeto operación determinado
     */
    @Override
    protected Instruction getInst(int param) {
        return new Jump(param);
    }

    @Override
    protected void operation(ExecutionManager controlUnit, int value) {}

    /**
     * Método encargado de ejecutar la operación sobre la CPU.
     */
    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers) {
        executionManager.setPc(this.param);
    }
}
