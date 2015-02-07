package mv.strategies;

/**
 * Describes the output configuration
 *
 * @author Borja
 */
public interface OutStrategy {
    abstract void open();

    abstract void write(char c);

    abstract void close();
}
