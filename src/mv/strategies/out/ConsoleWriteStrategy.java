package mv.strategies.out;

import mv.strategies.OutStrategy;

/**
 * Output configuration when no output file has been given
 *
 * @author Borja
 */
public class ConsoleWriteStrategy implements OutStrategy {

    @Override
    public void open() {
        // There is nothing to open
    }

    @Override
    public void write(char c) {
        System.out.print(c);
    }

    @Override
    public void close() {
        // There is nothing to close
    }
}