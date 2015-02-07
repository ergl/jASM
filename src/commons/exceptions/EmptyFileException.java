package commons.exceptions;

import java.io.File;

/**
 * ASM source code file is empty
 *
 * @author Borja
 */
public class EmptyFileException extends Exception {

    public EmptyFileException(File file) {
        super("Error: Couldn't read " + file + ": File is empty.");
    }

    public String getMessage() {
        return super.getMessage();
    }
}
