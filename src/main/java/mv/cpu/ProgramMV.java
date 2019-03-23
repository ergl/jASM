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

package mv.cpu;

import commons.exceptions.BadProgramException;
import commons.exceptions.UnrecoverableException;
import mv.ins.Instruction;
import mv.ins.InstructionParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;
import java.util.Vector;

public class ProgramMV {

    private static final char COMMENT_DELIMITER = ';';
    private static final char FUNCTION_DELIMITER = '@';
    private static final char FUNCTION_START_DELIM = ':';

    private static final int MAX_PROGRAM_SIZE = 100;
    private static final String MSG_INTRO = "Input your program here\n(END to stop): ";
    private static final String MSG_PROMPT = "> ";
    private static final String MSG_SHOW = "Program to be executed: ";
    private static final String END_TOKEN = "END";

    private Vector<Instruction> program;
    private final Env functionEnv;
    private int programLine;

    public ProgramMV() {
        this.program = new Vector<>();
        this.functionEnv = new Env();
    }

    private void addFunction(Integer position, String name) {
        functionEnv.set(position, name);
    }

    private Optional<Integer> get(String name) {
        return functionEnv.find(name);
    }

    /**
     * Parse the source code from stdin
     *
     * Will be executed until user inputs the END_TOKEN
     * (Syntax errors will be notified and that line will be discarded)
     *
     * @param scanner open stdin scanner
    */
    public void readProgram(Scanner scanner) {
        System.out.println(MSG_INTRO);
        System.out.print(MSG_PROMPT);
        String input;
        programLine = 0;
        while (!(input = scanner.nextLine()).equalsIgnoreCase(END_TOKEN)) {
            try {
                parse(input);
            } catch (UnrecoverableException e) {
                System.err.println(e.getMessage());
            }
            System.out.print(MSG_PROMPT);
        }
    }

    /**
     * Parse the source code from a file
     *
     * Will be executed until EOF or syntax error
     *
     * @param file source code file
     * @throws commons.exceptions.UnrecoverableException
     */
    public void readProgram(String file) throws UnrecoverableException {
        BufferedReader bf = null;
        Path input = Paths.get(file);
        programLine = 0;
        try {
            bf = Files.newBufferedReader(input, Charset.defaultCharset());
            String line;

            while (null != (line = bf.readLine())) {
                parse(line);
            }

        } catch (IOException e) {
            System.err.println("Fatal: Couldn't read source file. \nExiting...");
            System.exit(2);
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(2);
            }
        }
    }

    /**
     * Parses input into instructions
     *
     * @param line input line
     *
     * @throws UnrecoverableException with syntax error or other badly formed string
     */
    private void parse(String line) throws UnrecoverableException {

        if (line == null || line.isEmpty()) {
            return;
        }

        Instruction inst;
        String result = line.trim();

        if (result.isEmpty() || result.startsWith(String.valueOf(COMMENT_DELIMITER))) {
            return;
        }

        if (result.contains(String.valueOf(COMMENT_DELIMITER))) {
            result = result.split(String.valueOf(COMMENT_DELIMITER))[0];
            if (result.isEmpty()) {
                return;
            }
        }

        if (result.startsWith(String.valueOf(FUNCTION_DELIMITER))) {
            String[] tokens = result.split("\\s", 2);
            if (tokens[0].endsWith(String.valueOf(FUNCTION_START_DELIM))) {
                addFunction(programLine, tokens[0].substring(0, tokens[0].length() - 1));
                if (tokens.length != 2 || tokens[1].isEmpty()) {
                    return;
                }
                result = tokens[1];
            } else {
                throw new BadProgramException(tokens[0]);
            }
        }

        if ((inst = InstructionParser.parse(result)) != null) {
            addInstruction(inst);
            programLine++;
        } else {
            throw new BadProgramException(result);
        }
    }

    private void addInstruction(Instruction inst) {
        if (this.program.size() < MAX_PROGRAM_SIZE) {
            this.program.add(inst);
        }
    }

    public Instruction getInstructionAt(int i) {
        if (i >= 0 || i < MAX_PROGRAM_SIZE) {
            return program.elementAt(i);
        } else {
            return null;
        }
    }

    // Get the number of instructions in the program
    public int getCont() {
        return program.size();
    }

    public String[] displayContent() {
        String[] formattedProgram = new String[program.size()];

        for (int i = 0; i < this.program.size(); i++) {
            Instruction tempInst = getInstructionAt(i);
            formattedProgram[i] = " " + tempInst.toString();
        }
        return formattedProgram;
    }

    @Override
    public String toString() {
        String formattedProgram = "";
        for (int i = 0; i < program.size(); i++) {
            Instruction tempInst = getInstructionAt(i);
            formattedProgram += System.lineSeparator() + i + ": " + tempInst.toString();
        }
        return (MSG_SHOW + formattedProgram);
    }
}
