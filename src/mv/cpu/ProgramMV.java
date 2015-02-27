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
import java.util.Scanner;
import java.util.Vector;

public class ProgramMV {

    private static char COMMENT_DELIMITER = ';';

    private static final int MAX_PROGRAM_SIZE = 100;
    private static final String MSG_INTRO = "Input your program here\n(END to stop): ";
    private static final String MSG_PROMPT = "> ";
    private static final String MSG_ERROR = "Error: Syntax error";
    private static final String MSG_SHOW = "Program to be executed: ";
    private static final String END_TOKEN = "END";

    private Vector<Instruction> program;

    public ProgramMV() {
        this.program = new Vector<>();
    }

    /**
     * Parse the source code from stdin
     *
     * Will be executed until user inputs the END_TOKEN
     *
     * @param scanner open stdin scanner
    */
    public void readProgram(Scanner scanner) {
        System.out.println(MSG_INTRO);
        System.out.print(MSG_PROMPT);
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase(END_TOKEN)) {
            Instruction inst = InstructionParser.parse(input);
            if (inst != null) {
                addInstruction(inst);
            } else {
                System.out.println(MSG_ERROR);
            }
            System.out.print(MSG_PROMPT);
            input = scanner.nextLine();
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
        Instruction inst;
        BufferedReader bf = null;
        Path input = Paths.get(file);

        try {
            bf = Files.newBufferedReader(input, Charset.defaultCharset());
            String line;

            while (null != (line = bf.readLine())) {
                line = line.trim();

                if (line.startsWith(String.valueOf(COMMENT_DELIMITER))) {
                    continue;
                }

                String[] tmp = line.split(String.valueOf(COMMENT_DELIMITER));
                StringBuilder sb = new StringBuilder();
                sb.append(tmp[0]); // get the line before the comment

                if (sb.toString().isEmpty()) {
                    continue;
                }

                inst = InstructionParser.parse(sb.toString());
                if (inst != null) {
                    addInstruction(inst);
                } else {
                    throw new BadProgramException(sb.toString());
                }
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
