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

package commons.exceptions;

import mv.ins.Instruction;

/**
 * When there aren't enough elements if the stack to perform an operation
 *
 * @author Borja
 */
public class StackException extends RecoverableException {

    public StackException(Instruction inst, int n) {
        super("Error: Execution " + inst + ": not enough elements in the stack (" + n + " elements)");
    }

    public String getMessage() {
        return super.getMessage();
    }
}
