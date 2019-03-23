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

package mv.strategies.out;

import mv.strategies.OutStrategy;

/**
 * Output configuration when no output file has been given
 *
 * @author Borja
 */
public class ConsoleWriteStrategy implements OutStrategy {

    @Override
    public void open() {
        // There is nothing to open
    }

    @Override
    public void write(char c) {
        System.out.print(c);
    }

    @Override
    public void close() {
        // There is nothing to close
    }
}