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

package mv.cpu;

/**
 * Individual Memory Cell
 * Each cell has a value and a reference (memory address)
 *
 * @author Borja
 */
public class MemCell {

    private int ref;
    private int val;

    public MemCell(int val, int ref) {
        this.ref = ref;
        this.val = val;
    }

    public void setVal(int value) {
        this.val = value;
    }

    public int getVal() {
        return this.val;
    }

    public int getPos() {
        return this.ref;
    }
}
