package mv.ins.instList.stackModifiers;

import commons.exceptions.*;
import mv.cpu.ExecutionManager;
import mv.cpu.Memory;
import mv.cpu.OperandStack;
import mv.cpu.RegisterBank;
import mv.ins.Instruction;
import mv.ins.instList.OneParamInst;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

/**
 * Realiza una operación de LOAD con referencia indicada por la cima de la pila.
 * En caso de que sea una operación ilegal, vuelve a apilar dicho valor.
 *
 * @author Borja
 * @author Chaymae
 */
public class LoadInd extends OneParamInst {

    public LoadInd() {
        super("LOADIND");
    }

    @Override
    protected Instruction getInst() {
        return new LoadInd();
    }

    @Override
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out, RegisterBank registers) throws UnrecoverableException, RecoverableException {

        if (!stack.isEmpty()) {
            int read = stack.popValue();
            if (read >= 0) {
                if (!memory.isEmpty()) {
                    int pos = memory.getMemoryReference(read);
                    if (pos != -1) {
                        stack.pushValue(memory.loadValue(pos));
                    } else {
                        throw new MemoryException("La celda seleccionada está vacía.");
                    }
                } else {
                    throw new MemoryException("La celda seleccionada está vacía.");
                }
            } else {
                stack.pushValue(read);
                throw new OutOfBoundMemoryException(read);
            }
        } else {
            throw new StackException(this, stack.elements());
        }
    }
}
