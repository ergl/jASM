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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * VM Stack
 *
 * @author Borja
 */
public class OperandStack extends Watchable {

    private ArrayList<Integer> stack;

    public OperandStack() {
        stack = new ArrayList<>();
    }

    void flush() {
        stack.clear();
        this.setChanged();
        this.notifyViews(this.displayContent());
    }

    public void pushValue(Integer e) {
        stack.add(e);

        this.setChanged();
        this.notifyViews(this.displayContent());
    }

    public Integer popValue() {
        int value = stack.remove(elements() - 1);

        this.setChanged();
        this.notifyViews(this.displayContent());

        return value;
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int elements() {
        return stack.size();
    }

    public String displayContent() {
        if (stack.size() == 0) {
            return "";
        }

        ListIterator<Integer> li = stack.listIterator(stack.size());

        StringBuilder sb = new StringBuilder();
        boolean firstValue = true;

        while (li.hasPrevious()) {
            Integer n = li.previous();
            if (firstValue) {
                sb.append(n);
                firstValue = false;
            } else {
                sb.append("\n").append(n);
            }
        }

        return sb.toString();
    }

    public String toString() {
        Iterator<Integer> it = stack.iterator();
        if (!it.hasNext()) {
            return "Operand Stack: <empty>";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Operand Stack: ");

        do {
            sb.append(it.next());
            sb.append(' ');
        } while (it.hasNext());

        return sb.append("").toString();
    }
}
