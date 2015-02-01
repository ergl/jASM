package mv.strategies;

/**
 * Describe la configuración de salida.
 *
 * @author Borja
 * @author Chaymae
 */
public interface OutStrategy {
    abstract void open();

    abstract void write(char c);

    abstract void close();
}
