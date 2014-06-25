package mv.ins;

import mv.ins.instList.arithmetics.*;
import mv.ins.instList.branches.*;
import mv.ins.instList.misc.Halt;
import mv.ins.instList.stackModifiers.*;
import mv.ins.instList.summitModifiers.*;

/**
 * Genera una instruccción a partir del input del usuario.
 * 
 * @author Borja
 * @author Chaymae
 */
public class InstructionParser {

    private static Instruction[] instructionList = {
        /* Arithmetics */
        new Add(),      new Sub(),      new Mul(),          new Div(),
        new And(),      new Equals(),   new GreaterThan(),  new LessEqual(),
        new LessThan(), new Not(),      new Or(),

        /* Branches */
        new Bf(),       new Bt(),       new Jump(),         new JumpInd(),
        new RBf(),      new RBt(),      new RJump(), 

        /* Stack Modifiers */
        new Push(),     new Store(),    new Load(), 
        new LoadInd(),  new StoreInd(),

        /* Summit Modifiers */
        new Pop(),      new Dup(),      new Flip(),
        new Neg(),      new In(),       new Out(),

        /* Misc */
        new Halt(),

        /* Examen */
        new Move(), 	new Loopdec()

    };

    /**
     * Devuelve un objeto Instruction que podra ser procesado por la cpu.
     * El input debe estar correctamente formateado o de lo contrario ignorara
     * la orden y se interpretara como un error.
     *  
     * @param input string describiendo una instrucción
     * @return instrucción correctamente formateado
     */
    public static Instruction parse(String input) {
        for(Instruction inst : instructionList) {
            Instruction tmp = inst.parse(input);
            if(tmp != null)
                return tmp;
        }
        return null;
    }
}
