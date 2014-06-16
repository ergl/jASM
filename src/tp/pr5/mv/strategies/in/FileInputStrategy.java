package tp.pr5.mv.strategies.in;

import tp.pr5.mv.strategies.InStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Configuración de entrada cuando se especifica un archivo de entrada.
 * Dado que todas las posibles excepciones que pueden darse ya habrán sido lanzadas y
 * solucionadas antes de ejecutar los métodos de esta clase, no hacemos nada con ellas aquí.
 * 
 * @author Borja
 * @author Chaymae
 */
public class FileInputStrategy implements InStrategy  {

    private FileReader fr;
    private File inFile;

    @Override
    public void open(Path filePath) {
        try {
            this.inFile = filePath.toFile();
            this.fr = new FileReader(this.inFile);
        } catch (FileNotFoundException e) {
            // do nothing, already checked
        }
    }

    /**
     * Si por algún motivo no puede leerse de fichero, o ya se ha llegado al final de él, devolveremos -1 (marca de final de fichero)
     */
    @Override
    public int read() {
        int value;
        try {
            if(fr.ready())
                value = fr.read();
            else value = -1;
        } catch (IOException e) {
            value = -1;
        }
        return value;
    }

    @Override
    public void close() {
        try {
            this.fr.close();
        } catch(IOException e) {
            System.exit(2);
        }
    }

    @Override
    public char[] showFile() {
        StringBuilder fileContent = new StringBuilder((int) inFile.length());
        String lineSeparator = System.lineSeparator();
        Scanner sc;
        try {
            sc = new Scanner(inFile);
            try {
                while(sc.hasNextLine())
                    fileContent.append(sc.nextLine() + lineSeparator);
                return fileContent.toString().trim().toCharArray();
            } finally {
                sc.close();
            }
        } catch (FileNotFoundException e) {
            return "".toCharArray();
        }
    }
}
