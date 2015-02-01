package mv.strategies.out;

import mv.strategies.OutStrategy;

public class NullWriteStrategy implements OutStrategy {

    @Override
    public void open() {
        // There is nothing to open
    }

    @Override
    public void write(char c) {
        // There is nowhere to write

    }

    @Override
    public void close() {
        // There is nothing to close
    }
}