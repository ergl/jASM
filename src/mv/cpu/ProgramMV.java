package mv.cpu;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;

import commons.exceptions.BadProgramException;
import commons.exceptions.UnrecoverableException;
import mv.ins.Instruction;
import mv.ins.InstructionParser;

public class ProgramMV {

    private static final int MAX_PROGRAM_SIZE = 100;
    private static final String MSG_INTRO = "Introduce el programa fuente: ";
    private static final String MSG_PROMPT = "> ";
    private static final String MSG_ERROR = "Error: Instruccion incorrecta";
    private static final String MSG_SHOW = "El programa introducido es: ";
    private static final String END_TOKEN = "END";

    private Vector<Instruction> program;

    public ProgramMV() {
        this.program = new Vector<>();
    }

    /**
     * Método encargado de leer el programa, mostrando al usuario el prompt y los mensajes de información o error.
     * La ejecución termina cuando el usuario introduce el valor definido por PR_END_TOKEN. Al terminar de leer se muestra el programa cargado.
     *
     * @param scanner de entrada desde Main
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
     * Lee el programa desde un fichero. Admite comentarios en el fichero
     * Dado que las excepciones de fichero no existente ya han sido comprobadas y solucionadas antes,
     * no hacemos nada con ellas aquí
     *
     * @param file fichero donde se encuentra el programa
     */
    public void readProgram(String file) throws UnrecoverableException {

        BufferedReader bf = null;
        Path input = Paths.get(file);
        Instruction inst;

        try {
            bf = Files.newBufferedReader(input, Charset.defaultCharset());
            String line = bf.readLine();

            while (line != null) {
                line = line.trim();

                if (!line.startsWith(";")) {
                    String[] tmp = line.split(";");
                    StringBuilder sb = new StringBuilder();
                    sb.append(tmp[0]);

                    if (!sb.toString().isEmpty()) {
                        inst = InstructionParser.parse(sb.toString());
                        if (inst != null) {
                            addInstruction(inst);
                        } else {
                            throw new BadProgramException(sb.toString());
                        }
                    }
                }
                line = bf.readLine();
            }

        } catch (IOException e) {
            System.exit(2);
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * Añade una instrucción al programa.
     *
     * @param inst Instrucción a introducir
     */
    private void addInstruction(Instruction inst) {
        if (this.program.size() < MAX_PROGRAM_SIZE) {
            this.program.add(inst);
        }
    }

    /**
     * Devuelve la instrucción en la posición definida.
     *
     * @param i posición de una instrucción en el programa
     *
     * @return Devuelve la instrucción en la posición definida, si la posición no existe, devolverá una instrucción nula
     */
    public Instruction getInstructionAt(int i) {
        if (i >= 0 || i < MAX_PROGRAM_SIZE) {
            return program.elementAt(i);
        } else {
            return null;
        }
    }

    /**
     * Devuelve el número de instrucciones del programa.
     *
     * @return el número de instrucciones en el programa
     */
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
