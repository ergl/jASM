package tp.pr5.mv.strategies.out;

import tp.pr5.mv.strategies.OutStrategy;

/**
 * Configuración de salida cuando no se especifica un archivo de salida
 * 
 * @author Borja
 * @author Chaymae
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
