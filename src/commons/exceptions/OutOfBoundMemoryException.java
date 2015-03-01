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

/**
 * Trying to read or write in an illegal memory address
 *
 * @author Borja
 */
public class OutOfBoundMemoryException extends UnrecoverableException {

    public OutOfBoundMemoryException(int illegalReference) {
        super("Error: Memory address is out of bounds. (Trying to read from / write to address: " + illegalReference + ")");
    }

    public String getMessage() {
        return super.getMessage();
    }
}
