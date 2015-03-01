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

import commons.watcherPattern.Watchable;
import mv.strategies.InStrategy;

import java.nio.file.Path;

public class WindowIn extends Watchable implements InStrategy {

    private InStrategy inStr;

    public WindowIn(InStrategy _inStr) {
        this.inStr = _inStr;
    }

    @Override
    public void open(Path filePath) {
        inStr.open(filePath);
    }

    @Override
    public int read() {
        int value = this.inStr.read();

        this.setChanged();
        this.notifyViews(value);

        return value;
    }

    @Override
    public void close() {
        this.inStr.close();
    }

    @Override
    public char[] showFile() {
        return inStr.showFile();
    }

    @Override
    public Path getFilePath() {
        return inStr.getFilePath();
    }
}
