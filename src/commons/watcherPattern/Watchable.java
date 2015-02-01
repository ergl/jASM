package commons.watcherPattern;

import java.util.ArrayList;

/**
 * Ver Observable.
 * Implementación propia de la clase Observable en el patrón Observer.
 * Thread safe
 * 
 * @author Borja
 * @author Chaymae
 */
public class Watchable {

    private boolean changedFlag = false;
    private ArrayList<Watcher> watcherList;

    public Watchable() {
        watcherList = new ArrayList<>();
    }

    public synchronized void addWatcher(Watcher w) {
        if(w == null)
            throw new NullPointerException();

        if(!watcherList.contains(w))
            watcherList.add(w);
    }

    public synchronized void deleteWatcher(Watcher w) {
        if(watcherList.contains(w))
            watcherList.remove(w);
    }

    public void notifyViews() {
        this.notifyViews(null);
    }

    public void notifyViews(Object arg) {

        Object[] arrLocal;

        synchronized(this) {
            if(!changedFlag)
                return;

            arrLocal =  watcherList.toArray();
            clearChanged();
        }

        for(int i = arrLocal.length - 1 ; i >= 0; i--)
            ((Watcher)arrLocal[i]).updateDisplays(this, arg);

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
