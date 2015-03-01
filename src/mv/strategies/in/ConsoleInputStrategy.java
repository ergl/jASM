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

package mv.strategies.in;

import mv.strategies.InStrategy;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Input configuration when no input file has been given
 *
 * @author Borja
 */
public class ConsoleInputStrategy implements InStrategy {

    @Override
    public void open(Path filePath) {
        // There is nothing to open
    }

    @Override
    public int read() {
        try {
            return System.in.read();
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public void close() {
        // There is nothing to close
    }

    @Override
    public char[] showFile() {
        // There is no file to show
        return null;
    }

    @Override
    public Path getFilePath() {
        return null;
    }
}
