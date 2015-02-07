package mv.strategies.in;

import mv.strategies.InStrategy;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Input configuration when no input file has been given
 *
 * @author Borja
 */
public class ConsoleInputStrategy implements InStrategy {

    @Override
    public void open(Path filePath) {
        // There is nothing to open
    }

    @Override
    public int read() {
        try {
            return System.in.read();
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public void close() {
        // There is nothing to close
    }

    @Override
    public char[] showFile() {
        // There is no file to show
        return null;
    }

    @Override
    public Path getFilePath() {
        return null;
    }
}
