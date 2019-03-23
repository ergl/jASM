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
import java.util.ListIterator;

public class RegisterBank extends Watchable {

    private static int MAX_REGISTER = 4;
    private ArrayList<Register> registerList;

    public RegisterBank() {
        this.registerList = new ArrayList<Register>();
        init();
    }

    private void init() {
        for (int i = 0; i < MAX_REGISTER; i++) {
            registerList.add(new Register(0));
        }
    }

    public void store(int pos, int value) {
        this.registerList.get(pos).setContent(value);
        this.setChanged();
        this.notifyViews(displayContent());
    }

    public int load(int pos) {
        return this.registerList.get(pos).getContent();
    }

    private String displayContent() {
        ListIterator<Register> li = registerList.listIterator();

        StringBuilder sb = new StringBuilder();
        while (li.hasNext()) {
            Register n = li.next();
            sb.append(n.getContent()).append(" ");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        ListIterator<Register> li = registerList.listIterator();

        StringBuilder sb = new StringBuilder();
        sb.append("Register: ");

        while (li.hasNext()) {
            Register n = li.next();
            sb.append("[R").append(registerList.indexOf(n)).append("]: ").append(n).append(" ");
        }

        return sb.toString();
    }
}
