package commons.exceptions;

import java.io.File;

/**
 * Exceptción que ocurre cuando el archivo de entrada ASM se encuentra vacío.
 *
 * @author Borja
 * @author Chaymae
 */
@SuppressWarnings("serial")
public class EmptyFileException extends Exception {

    public EmptyFileException(File file) {
        super("Error al acceder " + file + ": El fichero se encuentra vacío.");
    }

    public String getMessage() {
        return super.getMessage();
    }
}
