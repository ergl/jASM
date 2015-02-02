package mv.strategies.out;

import mv.strategies.OutStrategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuración de salida cuando se especifica un archivo de salida
 *
 * @author Borja
 * @author Chaymae
 */
public class FileWriteStrategy implements OutStrategy {

    private File outFile;
    private String fileName;
    private Path outFilePath;
    private PrintWriter writer;

    public FileWriteStrategy(String file) {
        this.fileName = file;
    }

    /**
     * Abre el archivo de salida.
     * En caso de que el archivo no exista, crea un archivo TXT con el nombre indicado por el parámetro -o de la aplicación.
     * Dado que todas las posibles excepciones que pueden darse ya habrán sido lanzadas y
     * solucionadas antes de ejecutar éste método, no hacemos nada con ellas aquí.
     */
    @Override
    public void open() {
        this.outFilePath = Paths.get(this.fileName);

        if (Files.exists(this.outFilePath)) {
            try {
                this.writer = new PrintWriter(new FileWriter(this.outFilePath.toFile()));
                this.outFile = this.outFilePath.toFile();
            } catch (IOException e) {
                System.exit(2);
            }
        } else {
            this.outFile = new File(this.fileName);
            try {
                this.writer = new PrintWriter(new FileWriter(this.outFile));
            } catch (IOException e) {
                System.exit(2);
            }
        }

    }

    @Override
    public void write(char c) {
        writer.print(c);
    }

    @Override
    public void close() {
        writer.close();
    }
}