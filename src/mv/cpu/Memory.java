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

import commons.watcherPattern.Watchable;

/**
 * Memory Array.
 * Non-dynamic array of Memory Cells
 * @see mv.cpu.MemCell
 *
 * @author Borja
 */
public class Memory extends Watchable {

    private static final int MAX_MEMPOS = 100;

    private MemCell[] memArray;
    private int elements;

    public Memory() {
        this.memArray = new MemCell[MAX_MEMPOS];
        this.elements = 0;
    }

    void flush() {
        memArray = new MemCell[MAX_MEMPOS];
        elements = 0;
        this.setChanged();
        this.notifyViews(this.displayContent());
    }

    public boolean isEmpty() {
        return elements == 0;
    }

    public boolean isFull() {
        return elements == MAX_MEMPOS;
    }

    public int loadValue(int pos) {
        return memArray[pos].getVal();
    }

    /**
     * Stores a value on the first memory position.
     * Cells can be overwritten
     *
     * @param val value to be written
     * @param ref memory reference in which that value will be stored
     */
    // TODO: Check if a return value is needed
    public boolean storeValue(int val, int ref) {
        boolean success = true;
        int oldRef = getMemoryReference(ref);

        this.setChanged();

        if (oldRef != -1) {
            memArray[oldRef].setVal(val);
            this.notifyViews(this.displayContent());
        } else if (!isFull()) {
            if (!isEmpty()) {
                int i = elements - 1;

                while (i >= 0 && (memArray[i].getPos() > ref)) {
                    memArray[i + 1] = memArray[i];
                    i--;
                }

                memArray[i + 1] = new MemCell(val, ref);
                elements++;

                this.notifyViews(this.displayContent());
            } else {
                memArray[elements] = new MemCell(val, ref);
                elements++;

                this.notifyViews(this.displayContent());
            }
        } else {
            success = false;
        }

        return success;
    }

    /**
     * Gets the position in the memory array of a given reference.
     *
     * @param ref reference to be found
     *
     * @return position of said reference in the array, -1 if not found
     *
     * @see #binaryRefSearch(int, int, int)
     */
    public int getMemoryReference(int ref) {
        return binaryRefSearch(ref, 0, elements - 1);
    }

    /**
     * Searches for a reference in the array
     *
     * @param ref reference to be found
     * @param ini initial search point
     * @param fin final search point
     *
     * @return position of said reference in the array, -1 if not found
     */
    private int binaryRefSearch(int ref, int ini, int fin) {
        int pos = -1;

        if (ini > fin) {
            return pos;
        } else {
            pos = (ini + fin) / 2;
            if (ref < this.memArray[pos].getPos()) {
                return binaryRefSearch(ref, ini, pos - 1);

            } else if (ref > this.memArray[pos].getPos()) {
                return binaryRefSearch(ref, pos + 1, fin);

            } else {
                return pos;
            }
        }
    }

    public String displayContent() {
        if (!isEmpty()) {
            String formattedMem = "";
            for (int i = 0; i < elements; i++)
                formattedMem += memArray[i].getPos() + " " + memArray[i].getVal() + " ";

            return formattedMem;
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        if (!isEmpty()) {
            String formattedMem = "";
            for (int i = 0; i < elements; i++)
                formattedMem += "[" + memArray[i].getPos() + "]" + ":" + memArray[i].getVal() + " ";

            return "Memory: " + formattedMem;
        } else {
            return "Memory: <empty>";
        }
    }
}
