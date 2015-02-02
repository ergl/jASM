package mv.strategies;

import java.nio.file.Path;

/**
 * Describe la configuración de entrada.
 *
 * @author Borja
 * @author Chaymae
 */
public interface InStrategy {
    abstract void open(Path filePath);

    abstract int read();

    abstract char[] showFile();

    abstract void close();
    abstract Path getFilePath();
}
