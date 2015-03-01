/*
 * This file is part of jASM.
 *
 * jASM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jASM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jASM.  If not, see <http://www.gnu.org/licenses/>.
 */

package mv.ins;

import mv.ins.instList.arithmetics.*;
import mv.ins.instList.branches.*;
import mv.ins.instList.misc.Halt;
import mv.ins.instList.stackModifiers.*;
import mv.ins.instList.summitModifiers.*;

/**
 * Parses user input into instruction objects
 *
 * @author Borja
 */
public class InstructionParser {

    private static Instruction[] instructionList = {
        /* Arithmetics */
            new Add(), new Sub(), new Mul(),
            new Div(), new And(), new Equals(),
            new GreaterThan(), new LessEqual(),
            new LessThan(), new Not(), new Or(),
        
        /* Branches */
            new Bf(), new Bt(), new Jump(),
            new JumpInd(), new RBf(), new RBt(), new RJump(),
        
        /* Stack Modifiers */
            new Push(), new Store(), new Load(),
            new LoadInd(), new StoreInd(),
        
        /* Summit Modifiers */
            new Pop(), new Dup(), new Flip(),
            new Neg(), new In(), new Out(),
        
        /* Meta */
            new Halt(),
        
        /* TODO: ??? */
            new Move(),
            new Loopdec()
    };

    /**
     * Parses user input and creates an Instruction object that can be processed by the CPU
     * If the input is wrongly formatted it will ignore the order and it will be parsed as an error
     *
     * @param input user input describing an instruction
     *
     * @return Parsed instruction or null
     */
    public static Instruction parse(String input) {
        for (Instruction inst : instructionList) {
            Instruction tmp = inst.parse(input);
            if (tmp != null) {
                return tmp;
            }
        }
        return null;
    }
}
