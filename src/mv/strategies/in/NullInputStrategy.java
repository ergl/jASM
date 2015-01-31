package tp.pr5.mv.strategies.in;

import java.nio.file.Path;

import tp.pr5.mv.strategies.InStrategy;

public class NullInputStrategy implements InStrategy {

    @Override
    public void open(Path filePath) {
        // There is nothing to open
    }

    @Override
    public int read() {
        return -1;
    }

    @Override
    public void close() {
        // There is nothing to close
    }

    @Override
    public char[] showFile() {
        /* There is no file to show */
        return null;
    }
}
