package commons.watcherPattern;

/**
 * @see java.util.Observer
 */
public interface Watcher {

    void updateDisplays(Watchable o, Object arg);

}
