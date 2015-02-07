package mv.strategies.in;

import mv.strategies.InStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Input configuration when an input file has been given
 *
 * @author Borja
 */
public class FileInputStrategy implements InStrategy {

    private FileReader fr;
    private File inFile;
    private Path inFilePath;

    @Override
    public void open(Path filePath) {
        try {
            this.inFilePath = filePath;

            this.inFile = inFilePath.toFile();
            this.fr = new FileReader(this.inFile);
        } catch (FileNotFoundException e) {
            // do nothing, already checked
        }
    }

    @Override
    public int read() {
        int value;
        try {
            if (fr.ready()) {
                value = fr.read();
            } else {
                value = -1;
            }
        } catch (IOException e) {
            value = -1;
        }
        return value;
    }

    @Override
    public void close() {
        try {
            this.fr.close();
        } catch (IOException e) {
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
                while (sc.hasNextLine())
                    fileContent.append(sc.nextLine()).append(lineSeparator);
                return fileContent.toString().trim().toCharArray();
            } finally {
                sc.close();
            }
        } catch (FileNotFoundException e) {
            return "".toCharArray();
        }
    }

    @Override
    public Path getFilePath() {
        return this.inFilePath;
    }
}
