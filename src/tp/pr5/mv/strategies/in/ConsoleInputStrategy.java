package tp.pr5.mv.strategies.in;

import tp.pr5.mv.strategies.InStrategy;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Configuración de entrada cuando no se especifica un archivo de entrada.
 * 
 * @author Borja
 * @author Chaymae
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
        /* There is no file to show */
        return null;
    }
}
