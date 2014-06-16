package tp.pr5.mv.ins.instList.branches;

import tp.pr5.mv.cpu.*;
import tp.pr5.mv.ins.Instruction;
import tp.pr5.mv.strategies.*;

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
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out) {
        executionManager.setPc(this.param);
    }
}
