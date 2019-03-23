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

package commons.watcherPattern;

import java.util.ArrayList;

/**
 * @see java.util.Observable
 */
// TODO: Why can't we use the built-in class Observable?
public class Watchable {
    private boolean changedFlag = false;
    private ArrayList<Watcher> watcherList;

    public Watchable() {
        watcherList = new ArrayList<>();
    }

    public synchronized void addWatcher(Watcher w) {
        if (w == null) {
            throw new NullPointerException();
        }

        if (!watcherList.contains(w)) {
            watcherList.add(w);
        }
    }

    public synchronized void deleteWatcher(Watcher w) {
        if (watcherList.contains(w)) {
            watcherList.remove(w);
        }
    }

    public void notifyViews() {
        this.notifyViews(null);
    }

    public void notifyViews(Object arg) {

        Object[] arrLocal;

        synchronized(this) {
            if (!changedFlag) {
                return;
            }

            arrLocal = watcherList.toArray();
            clearChanged();
        }

        for (int i = arrLocal.length - 1; i >= 0; i--)
            ((Watcher) arrLocal[i]).updateDisplays(this, arg);

    }

    public synchronized void deleteWatchers() {
        watcherList.clear();
    }


    public synchronized void setChanged() {
        changedFlag = true;
    }


    public synchronized boolean hasChanged() {
        return changedFlag;
    }

    public synchronized void clearChanged() {
        changedFlag = false;
    }

    public synchronized int countWatchers() {
        return watcherList.size();
    }
}
