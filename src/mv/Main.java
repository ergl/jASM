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

package mv;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import commons.exceptions.EmptyFileException;
import commons.exceptions.UnrecoverableException;
import mv.cpu.CPU;
import mv.cpu.ProgramMV;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;
import mv.strategies.in.ConsoleInputStrategy;
import mv.strategies.in.FileInputStrategy;
import mv.strategies.in.NullInputStrategy;
import mv.strategies.in.WindowIn;
import mv.strategies.out.ConsoleWriteStrategy;
import mv.strategies.out.FileWriteStrategy;
import mv.strategies.out.NullWriteStrategy;
import mv.strategies.out.WindowOut;
import ui.SwingController;
import ui.SwingView;
import ui.TextController;
import ui.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * jASM
 *
 * @author Borja
 */
public class Main {

    private static final String ERR_CLI_HEAD = "Wrong usage: ";
    private static final String SHOW_CLI_HELP = "\nUse -h|--help for more details.";
    private static final String ERR_BAD_MODE = ERR_CLI_HEAD + "unknown execution mode (-m|--mode)" + SHOW_CLI_HELP;

    private static final String ERR_NO_IN = "Error: Couldn't open input file";
    private static final String ERR_NO_ASM = "Error: No ASM source file given" + SHOW_CLI_HELP;
    private static final String ERR_ASM_NOT_FOUND = "Error: Couldn't open ASM source file (file not found)" + SHOW_CLI_HELP;

    public static enum ExecutionMode {
        INTERACTIVE, BATCH, WINDOW
    }

    /**
     * Processes execution mode arguments and starts the program
     *
     * @see mv.Main#optionSelector(String[])
     */
    public static void main(String[] args) {

        SetupConfigurator setupConfiguration;
        ExecutionMode mode;
        String asmFile, inFile, outFile;
        InStrategy inStr;
        OutStrategy outStr;
        boolean writeLog;

        /**
         * @see mv.Main#optionSelector(String[])
         */
        setupConfiguration = optionSelector(args); // parses all input arguments

        if (setupConfiguration == null) {
            System.exit(1);
        }

        mode = setupConfiguration.getMode();
        asmFile = setupConfiguration.getAsm();
        inFile = setupConfiguration.getIn();
        outFile = setupConfiguration.getOut();
        inStr = setupConfiguration.getInputStrategy();
        outStr = setupConfiguration.getOutputStrategy();
        writeLog = setupConfiguration.getLogOption();

        if (!setupIO(inFile, outFile, inStr, outStr)) {
            System.exit(1);
        }

        switch (mode) {
            case INTERACTIVE:
                doInteractive(asmFile, inStr, outStr, writeLog);
                break;

            case WINDOW:
                doVisual(asmFile, inStr, outStr, writeLog);
                break;

            case BATCH:
                doBatch(asmFile, inStr, outStr, writeLog);
                break;

            default:
                doBatch(asmFile, inStr, outStr, writeLog);
        }
    }

    /**
     * Starts the program in interactive mode.
     *
     * @param asmFileString ASM source code filename
     * @param inStr input mode configuration
     * @param outStr output mode configuration
     * @param writeLog write to log file?
     */
    private static void doInteractive(String asmFileString, InStrategy inStr, OutStrategy outStr, boolean writeLog) {

        TextView view;
        TextController controller;

        Scanner sc = new Scanner(System.in);

        ProgramMV program = new ProgramMV();
        CPU cpu = new CPU(inStr, outStr, writeLog);

        if (setupProgramFile(asmFileString)) {
            try {
                program.readProgram(asmFileString);
            } catch (UnrecoverableException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        } else {
            program.readProgram(sc);
        }

        controller = new TextController(cpu, program, inStr, outStr);
        view = new TextView(controller, ExecutionMode.INTERACTIVE);

        controller.addView(view);
        view.enable();
    }

    /**
     * Starts the program in visual mode (GUI).
     *
     * All i/o streams get initialized in SwingController
     *
     * @param asmFileString ASM source code filename
     * @param inStr input mode configuration
     * @param outStr output mode configuration
     * @param writeLog write to log file?
     */
    private static void doVisual(String asmFileString, InStrategy inStr, OutStrategy outStr, boolean writeLog) {

        SwingView view;
        SwingController controller;

        ProgramMV program = new ProgramMV();
        CPU cpu = new CPU(inStr, outStr, writeLog);

        if (setupProgramFile(asmFileString)) {
            try {
                program.readProgram(asmFileString);
            } catch (UnrecoverableException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }

        controller = new SwingController(cpu, program, inStr, outStr);
        view = new SwingView(controller);

        controller.addView(view);
        view.enable();
    }

    /**
     * Starts the program in batch mode (Takes the ASM source code and runs it).
     *
     * @param asmFileString ASM source code filename
     * @param inStr input mode configuration
     * @param outStr output mode configuration
     * @param writeLog write to log file?
     */
    private static void doBatch(String asmFileString, InStrategy inStr, OutStrategy outStr, boolean writeLog) {

        TextView view;
        TextController controller;

        ProgramMV program = new ProgramMV();
        CPU cpu = new CPU(inStr, outStr, writeLog);

        if (setupProgramFile(asmFileString)) {
            try {
                program.readProgram(asmFileString);
            } catch (UnrecoverableException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }

        controller = new TextController(cpu, program, inStr, outStr);
        view = new TextView(controller, ExecutionMode.BATCH);

        controller.addView(view);
        view.enable();
    }

    /**
     * Initializes the IO of the program.
     * Opens the input and the output file, checks for any errors on them, etc
     *
     * @param inFileString input filename (for IN instructions)
     * @param outFileString output filename (for OUT instructions)
     * @param inStr input mode configuration
     * @param outStr output mode configuration
     *
     * @return the success of the operation
     */
    private static boolean setupIO(String inFileString, String outFileString,
                                   InStrategy inStr, OutStrategy outStr) {

        Path inFilePath;

        if (inFileString != null) {
            inFilePath = Paths.get(inFileString);
            if (Files.exists(inFilePath)) {
                inStr.open(inFilePath);
            } else {
                System.err.println(ERR_NO_IN + "(" + inFileString + ")" + SHOW_CLI_HELP);
                return false;
            }
        }

        if (outFileString != null) {
            outStr.open();
        }

        return true;
    }

    /**
     * Opens the ASM source code file and checks for any errors on it.
     * Returns false if the user did not specify a file path, if it doesn't exist or if it's empty
     *
     * @param asmFileString ASM source code filename

     * @return the success of the operation
     */
    private static boolean setupProgramFile(String asmFileString) {

        boolean success = false;
        Path inFilePath;
        File asmFile;

        if (asmFileString != null) {
            try {
                inFilePath = Paths.get(asmFileString);
                asmFile = new File(inFilePath.toString());
                if (Files.exists(inFilePath)) {
                    if (asmFile.length() != 0) {
                        success = true;
                    } else {
                        throw new EmptyFileException(asmFile);
                    }
                } else {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException e) {
                System.err.println(ERR_ASM_NOT_FOUND);
                success = false;

            } catch (EmptyFileException e) {
                System.err.println(e.getMessage());
                success = false;
            }
        }

        return success;
    }

    /**
     * Sets up the command line options and parses the user arguments
     *
     * @param args User input arguments
     *
     * @return SetupConfigurator object with all arguments set. Returns null if a parsing error happens
     * @see mv.Main.SetupConfigurator
     */
    private static SetupConfigurator optionSelector(String[] args) {

        CommandLine line;
        CommandLineParser parser = new BasicParser();
        Options options = new Options();
        boolean writeLog = false;

        options.addOption("a", "asm", true, "ASM source code file. Can be omitted in interactive mode");
        options.addOption("h", "help", false, "Shows this help");
        options.addOption("i", "in", true, "Program input file");
        options.addOption("m", "mode", true, "Execution mode (batch | interactive | visual). Batch is the default mode");
        options.addOption("o", "out", true, "Program output file");
        options.addOption("d", "debug", false, "Prints cpu state to log file. Check status.log");

        try {
            line = parser.parse(options, args);
        } catch (ParseException exp) {
            System.err.println("Wrong usage: " + exp.getMessage() + SHOW_CLI_HELP);
            return null;
        }

        if (line.hasOption("h")) {
            showHelp();
            return null;
        }

        if (line.hasOption("d")) {
            writeLog = true;
        }

        if (line.hasOption("m")) {
            String s = line.getOptionValue("m");
            if (s.equalsIgnoreCase(ExecutionMode.BATCH.name())) {
                return setupMode(ExecutionMode.BATCH, line, writeLog);

            } else if (s.equalsIgnoreCase(ExecutionMode.INTERACTIVE.name())) {
                return setupMode(ExecutionMode.INTERACTIVE, line, writeLog);

            } else if (s.equalsIgnoreCase(ExecutionMode.WINDOW.name())) {
                return setupMode(ExecutionMode.WINDOW, line, writeLog);

            } else {
                System.err.println(ERR_BAD_MODE);
                return null;
            }
        } else {
            return setupMode(ExecutionMode.BATCH, line, writeLog);
        }
    }

    /**
     * Parses the execution mode and the other io arguments. Shows error message if those arguments are incompatible.
     *
     * @param mode execution mode
     * @param line rest of the user input
     * @param writeLog write to log file?
     *
     * @return SetupConfigurator object with all arguments set.
     */
    private static SetupConfigurator setupMode(ExecutionMode mode, CommandLine line, boolean writeLog) {

        String asmFile, inFile, outFile;
        asmFile = inFile = outFile = null;

        if (line.hasOption("a")) {
            asmFile = line.getOptionValue("a");
            if (line.hasOption("i")) {
                inFile = line.getOptionValue("i");
            }

            if (line.hasOption("o")) {
                outFile = line.getOptionValue("o");
            }
        } else {
            if (mode == ExecutionMode.BATCH || mode == ExecutionMode.WINDOW) {
                System.err.println(ERR_NO_ASM);
                return null;
            } else {
                if (line.hasOption("i")) {
                    inFile = line.getOptionValue("i");
                }
                if (line.hasOption("o")) {
                    outFile = line.getOptionValue("o");
                }
            }
        }

        return new SetupConfigurator(mode, asmFile, inFile, outFile, writeLog);
    }

    private static void showHelp() {
        System.out.println("usage: jASM -a <asmfile> [-h] [-i <infile>] [-m <mode>] [-o <outfile>]");
        System.out.println(" -a,--asm <asmfile>  ASM source code file. Can be omitted in interactive mode");
        System.out.println(" -h,--help           Shows this help");
        System.out.println(" -i,--in <infile>    Program input file");
        System.out.println(" -m,--mode <mode>    Execution mode (batch | interactive | visual). Batch is the default mode");
        System.out.println(" -o,--out <outfile>  Program output file");
        System.out.println(" -d, --debug		 Prints cpu state to log file. Check status.log");
    }

    /**
     * Sets the I/O strategy
     *
     * Different input args combinations will give different IO strategies.
     */
    private static class SetupConfigurator {

        private static ExecutionMode mode;
        private static String asmFile;
        private static String inFile;
        private static String outFile;

        private static InStrategy inStrategy;
        private static OutStrategy outStrategy;

        private static boolean writeLog;

        private SetupConfigurator(ExecutionMode mode, String asmFile, String inFile, String outFile, boolean writeLog) {
            SetupConfigurator.mode = mode;
            SetupConfigurator.asmFile = asmFile;
            SetupConfigurator.inFile = inFile;
            SetupConfigurator.outFile = outFile;
            SetupConfigurator.writeLog = writeLog;
        }

        private InStrategy getInputStrategy() {
            if (inFile != null) {
                inStrategy = new FileInputStrategy();
            } else {
                if (mode == Main.ExecutionMode.INTERACTIVE || mode == Main.ExecutionMode.WINDOW) {
                    inStrategy = new NullInputStrategy();
                } else {
                    inStrategy = new ConsoleInputStrategy();
                }
            }

            return new WindowIn(inStrategy);
        }

        private OutStrategy getOutputStrategy() {
            if (outFile != null) {
                outStrategy = new FileWriteStrategy(outFile);
            } else {
                if (mode == Main.ExecutionMode.INTERACTIVE || mode == Main.ExecutionMode.WINDOW) {
                    outStrategy = new NullWriteStrategy();
                } else {
                    outStrategy = new ConsoleWriteStrategy();
                }
            }

            return new WindowOut(outStrategy);
        }

        private ExecutionMode getMode() {
            return mode;
        }

        private String getAsm() {
            return asmFile;
        }

        private String getIn() {
            return inFile;
        }

        private String getOut() {
            return outFile;
        }

        private boolean getLogOption() {
            return writeLog;
        }
    }
}
