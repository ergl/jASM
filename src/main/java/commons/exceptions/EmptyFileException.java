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

import java.io.File;

/**
 * ASM source code file is empty
 *
 * @author Borja
 */
public class EmptyFileException extends Exception {

    public EmptyFileException(File file) {
        super("Error: Couldn't read " + file + ": File is empty.");
    }

    public String getMessage() {
        return super.getMessage();
    }
}
