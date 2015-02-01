package commons.watcherPattern;

/**
 * Ver Observer
 * Implementación propia de la clase Observer del patrón Observer.
 * 
 * @author Borja
 * @author Chaymae
 */
public interface Watcher {

    void updateDisplays(Watchable o, Object arg);

}
