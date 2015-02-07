package mv.strategies;

import java.nio.file.Path;

/**
 * Describes the input configuration
 *
 * @author Borja
 */
public interface InStrategy {
    abstract void open(Path filePath);

    abstract int read();

    abstract char[] showFile();

    abstract void close();

    abstract Path getFilePath();
}
