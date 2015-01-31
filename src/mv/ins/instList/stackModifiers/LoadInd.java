package tp.pr5.mv.ins.instList.stackModifiers;

import tp.pr5.commons.exceptions.*;
import tp.pr5.mv.cpu.ExecutionManager;
import tp.pr5.mv.cpu.Memory;
import tp.pr5.mv.cpu.OperandStack;
import tp.pr5.mv.ins.Instruction;
import tp.pr5.mv.ins.instList.OneParamInst;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;


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
    public void execute(ExecutionManager executionManager, Memory memory, OperandStack stack, InStrategy in, OutStrategy out)
            throws UnrecoverableException, RecoverableException {
        
        if(!stack.isEmpty()) {
            int read = stack.popValue();
            if(read >= 0) {
                if (!memory.isEmpty()) {
                    int pos = memory.getMemoryReference(read);
                    if (pos != -1)
                        stack.pushValue(memory.loadValue(pos));

                    else
                        throw new MemoryException("La celda seleccionada está vacía.");
                }
                else
                    throw new MemoryException("La celda seleccionada está vacía.");
            }
            else {
                stack.pushValue(read);
                throw new OutOfBoundMemoryException(read);
            }
        }
        else
            throw new StackException(this, stack.elements());
    }
}
