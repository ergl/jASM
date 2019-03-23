/*
 * This file is part of jASM.
 *
 * jASM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jASM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jASM.  If not, see <http://www.gnu.org/licenses/>.
 */

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
 * Output configuration when an output file has been given
 *
 * @author Borja
 */
public class FileWriteStrategy implements OutStrategy {

    private File outFile;
    private String fileName;
    private Path outFilePath;
    private PrintWriter writer;

    public FileWriteStrategy(String file) {
        this.fileName = file;
    }

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