package mv;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

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

/**
 * Ejecuta la interfaz de interaccion con la VM.
 *
 * @author Borja
 * @author Chaymae
 */
public class Main {

    private static final String SHOW_CLI_HELP = "\nUse -h|--help para más detalles.";
    private static final String ERR_MODE = "Uso incorrecto: Modo incorrecto (parametro -m|--mode)" + SHOW_CLI_HELP;
    private static final String ERR_ASM_NOT_FOUND = "Uso incorrecto: Error al acceder al fichero ASM (no existe el fichero)" + SHOW_CLI_HELP;
    private static final String ERR_NO_IN = "Uso incorrecto: Error al acceder al fichero de entrada (";
    private static final String ERR_NO_ASM = "Uso incorrecto: Fichero ASM no especificado." + SHOW_CLI_HELP;


    public static enum ExecutionMode {
        INTERACTIVE, BATCH, WINDOW
    }


    /**
     * Encargado de distribuir las tareas de parsear los argumentos y determinar el modo de ejecución,
     * así como llamar a los métodos de ejecución dependiendo de los argumentos introducidos.
     *
     * @param args los argumentos introducidos por el usuario
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

        /* Determina el modo de ejecución y las estrategias de entrada y salida, así como los contenidos de los archivos */
        setupConfiguration = optionSelector(args);

        if (setupConfiguration == null) {
            System.exit(1);
        }

        mode    =   setupConfiguration.getMode();
        asmFile =   setupConfiguration.getAsm();
        inFile  =   setupConfiguration.getIn();
        outFile =   setupConfiguration.getOut();
        inStr   =   setupConfiguration.getInputStrategy();
        outStr  =   setupConfiguration.getOutputStrategy();
        writeLog =  setupConfiguration.getLogOption();

        switch(mode) {
            case INTERACTIVE:
                doInteractive(asmFile, inFile, outFile, inStr, outStr, writeLog); break;
            
            case WINDOW:
                doVisual(asmFile, inFile, outFile, inStr, outStr, writeLog); break;
            
            case BATCH:
                doBatch(asmFile, inFile, outFile, inStr, outStr, writeLog); break;
            
            default:
                doBatch(asmFile, inFile, outFile, inStr, outStr, writeLog);
        }
    }

    /**
     * Ejecuta el sistema en modo Interactivo.
     * Inicializa los Paths de los ficheros introducidos y ejecuta el programa de manera similar a la anterior práctica.
     * Si no existe archivo ASM, se da la opción de introducir el programa por consola.
     * <p>
     * Todas las excepciones lanzadas por fallos en ficheros (porque no existan, etc) son capturadas y solucionadas a este nivel.
     *
     * @param asmFileString nombre del archivo ASM que contiene el código del programa
     * @param inFileString nombre del archivo TXT que contiene el input del programa
     * @param outFileString nombre del archivo TXT que sirve de output del programa
     * @param inStr configuración del modo de entrada del programa
     * @param outStr configuración del modo de salida del programa
     */
    private static void doInteractive(String asmFileString, String inFileString, String outFileString, InStrategy inStr, OutStrategy outStr, boolean writeLog) {

        TextView view;
        TextController controller;

        Scanner sc = new Scanner(System.in);
        ProgramMV program = new ProgramMV();
        CPU cpu =           new CPU(inStr, outStr, writeLog);
        Path inFilePath =   null;
        File asmFile;

        if (inFileString != null) {
            try {
                inFilePath = Paths.get(inFileString);
                if (Files.exists(inFilePath)) {
                    inStr.open(inFilePath);
                } else {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException e) {
                System.err.println(ERR_NO_IN + inFileString + ")" + SHOW_CLI_HELP);
                System.exit(1);
            }
        }

        if (outFileString != null) {
            outStr.open();
        }

        if (asmFileString != null) {
            try {
                inFilePath = Paths.get(asmFileString);
                asmFile = new File(inFilePath.toString());

                if (Files.exists(inFilePath)) {
                    if (asmFile.length() != 0) {
                        program.readProgram(asmFileString);
                    } else {
                        throw new EmptyFileException(asmFile);
                    }
                } else {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException e) {
                System.err.println(ERR_ASM_NOT_FOUND);
                System.exit(1);

            } catch (EmptyFileException efe) {
                System.err.println(efe.getMessage());
                System.exit(2);

            } catch (UnrecoverableException ure) {
                System.err.println(ure.getMessage());
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
     * Ejecuta la aplicación en modo visual.
     * La inicialización de todos los streams de entrada se llevan a cabo en el SwingController.
     * <p>
     * La vista tiene al controlador como atributo.
     * La cpu y el controlador tienen una referencia a la vista mediante el método addObserver
     * El borrado de observadores y el cierre de las Strategies se lleva a cabo en el controlador cuando se produce una
     * UnRecoverableException, cuando el usuario acaba de ejecutar el programa o cuando cierra la aplicación.
     *
     * @param asmFileString nombre del archivo ASM que contiene el código del programa
     * @param inFileString nombre del archivo TXT que contiene el input del programa
     * @param outFileString nombre del archivo TXT que sirve de output del programa
     * @param inStr configuración del modo de entrada del programa
     * @param outStr configuración del modo de salida del programa
     */
    private static void doVisual(String asmFileString, String inFileString, String outFileString, InStrategy inStr, OutStrategy outStr, boolean writeLog) {
        SwingView view;
        SwingController controller;

        Path inFilePath;
        File asmFile;

        ProgramMV program = new ProgramMV();
        CPU cpu =           new CPU(inStr, outStr, writeLog);

        if (inFileString != null) {
            try {
                inFilePath = Paths.get(inFileString);

                if (Files.exists(inFilePath)) {
                    inStr.open(inFilePath);
                } else {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException e) {
                System.err.println(ERR_NO_IN + inFileString + ")");
                System.exit(1);
            }
        }

        if (outFileString != null) {
            outStr.open();
        }

        if (asmFileString != null) {
            try {
                inFilePath = Paths.get(asmFileString);
                asmFile = new File(inFilePath.toString());

                if (Files.exists(inFilePath)) {
                    if (asmFile.length() != 0) {
                        program.readProgram(asmFileString);
                    } else {
                        throw new EmptyFileException(asmFile);
                    }
                } else {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException e) {
                System.err.println(ERR_ASM_NOT_FOUND);
                System.exit(1);

            } catch (EmptyFileException efe) {
                System.err.println(efe.getMessage());
                System.exit(2);

            } catch (UnrecoverableException ure) {
                System.err.println(ure.getMessage());
                System.exit(1);

            }
        }

        controller = new SwingController(cpu, program, inStr, outStr);
        view = new SwingView(controller);

        controller.addView(view);
        view.enable();
    }

    /**
     * Ejecuta el sistema en modo Batch (Transparente al usuario).
     * Inicializa los Paths de los ficheros introducidos y ejecuta todo el programa
     * <p>
     * Todas las excepciones lanzadas por fallos en ficheros (porque no existan, etc) son capturadas y solucionadas a este nivel.
     *
     * @param asmFileString nombre del archivo ASM que contiene el código del programa
     * @param inFileString nombre del archivo TXT que contiene el input del programa
     * @param outFileString nombre del archivo TXT que sirve de output del programa
     * @param inStr configuración del modo de entrada del programa
     * @param outStr configuración del modo de salida del programa
     */
    private static void doBatch(String asmFileString, String inFileString, String outFileString, InStrategy inStr, OutStrategy outStr, boolean writeLog) {
        TextView view;
        TextController controller;

        Path inFilePath;
        File asmFile;

        ProgramMV program = new ProgramMV();
        CPU cpu = new CPU(inStr, outStr, writeLog);
        
        if(inFileString != null) {
            try {
                inFilePath = Paths.get(inFileString);

                if (Files.exists(inFilePath)) {
                    inStr.open(inFilePath);
                } else {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException e) {
                System.err.println(ERR_NO_IN + inFileString + ")" + SHOW_CLI_HELP);
                System.exit(1);
            }
        }

        if (outFileString != null) {
            outStr.open();
        }

        if (asmFileString != null) {
            try {
                inFilePath = Paths.get(asmFileString);
                asmFile = new File(inFilePath.toString());

                if (Files.exists(inFilePath)) {
                    if (asmFile.length() != 0) {
                        program.readProgram(asmFileString);
                    } else {
                        throw new EmptyFileException(asmFile);
                    }
                } else {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException e) {
                System.err.println(ERR_ASM_NOT_FOUND);
                System.exit(1);

            } catch (EmptyFileException efe) {
                System.err.println(efe.getMessage());
                System.exit(2);

            } catch (UnrecoverableException ure) {
                System.err.println(ure.getMessage());
                System.exit(1);
            }
        }

        controller = new TextController(cpu, program, inStr, outStr);
        view = new TextView(controller, ExecutionMode.BATCH);

        controller.addView(view);
        view.enable();
    }

    /**
     * Muestra el modo de uso del programa, indicando los parámetros disponibles y sus valores.
     */
    private static void showHelp() {
        System.out.println("usage: mv.Main [-a <asmfile>] [-h] [-i <infile>] [-m <mode>] [-o <outfile>]");
        System.out.println(" -a,--asm <asmfile>  Fichero con el codigo en ASM del programa a ejecutar. " + "\n\t\t     Obligatorio en modo batch.");
        System.out.println(" -h,--help           Muestra esta ayuda ");
        System.out.println(" -i,--in <infile>    Entrada del programa de la maquina-p.");
        System.out.println(" -m,--mode <mode>    Modo de funcionamiento (batch | interactive). " + "Por defecto, batch. ");
        System.out.println(" -o,--out <outfile>  Fichero donde se guarda la salida del programa " + "de la maquina-p. ");
        System.out.println(" -d, --debug		  Genera un log con los estados de la cpu");
    }

    /**
     * Crea las opciones de parámetros que el usuario puede introducir.
     * Además, parsea los parámetros del sistema y llama a submétodos que ejecutan las acciones necesarias.
     * <p>
     * Todas las excepciones lanzadas por falta de parámetros o por errores al introducirlos son recogidas
     * y solucionadas a este nivel.
     *
     * @param args los argumentos introducidos por el usuario. Vienen desde main
     *
     * @return una configuración de ejecución debidamente inicializada
     */
    private static SetupConfigurator optionSelector(String[] args) {
        CommandLine line = null;
        CommandLineParser parser = new BasicParser();
        Options options = new Options();
        boolean writeLog = false;

        options.addOption("a", "asm",   true,   "Fichero con el codigo en ASM del programa a ejecutar. Obligatorio en modo batch.");
        options.addOption("h", "help",  false,  "Muestra esta ayuda");
        options.addOption("i", "in",    true,   "Entrada del programa de la maquina-p.");
        options.addOption("m", "mode",  true,   "Modo de funcionamiento (batch | interactive). Por defecto, batch.");
        options.addOption("o", "out",   true,   "Fichero donde se guarda la salida del programa de la maquina-p.");
        options.addOption("l", "log", 	false, 	"Guarda a un log el estado de la cpu a lo largo de la ejecución del programa");

        try {
            line = parser.parse(options, args);
        } catch (ParseException exp) {
            System.err.println("Uso incorrecto: " + exp.getMessage() + "\nUse -h|--help para más detalles.");
            System.exit(0);
        }

        if (line.hasOption("h")) {
            showHelp();
            System.exit(0);
        }
        
        if (line.hasOption("l")) {
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
                System.err.println(ERR_MODE);
                return null;
            }
        } else
            return setupMode(ExecutionMode.BATCH, line, writeLog);
    }

    /**
     * Determina el valor de los parámetros del programa, como ficheros de código, entrada y salida.
     * Además avisa de errores por incompatibildad entre valores de parámetros.
     *
     * @param mode modo de ejecución del programa
     * @param line string que contiene el valor del resto de parámetros
     *
     * @return una configuración de ejecución debidamente inicializada
     */
    private static SetupConfigurator setupMode(ExecutionMode mode, CommandLine line, boolean writeLog) {
        String asmFile = null,
                inFile = null,
                outFile = null;

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
                System.exit(1);
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

    /**
     * Determina la estrategia de entrada y de salida.
     * Encapsula todos los parámetros de ejecución en un objeto para mejor organización.
     *
     * @author Borja
     * @author Chaymae
     */
    private static class SetupConfigurator {

        private static ExecutionMode _mode;
        private static String _asmFile;
        private static String _inFile;
        private static String _outFile;

        private static InStrategy _inStrategy;
        private static OutStrategy _outStrategy;
        
        private static boolean _writeLog;

        private SetupConfigurator(ExecutionMode mode, String asmFile, String inFile, String outFile, boolean writeLog) {
            _mode    =  mode;
            _asmFile =  asmFile;
            _inFile  =  inFile;
            _outFile =  outFile;
            _writeLog = writeLog;
        }

        private InStrategy getInputStrategy() {
            if (_inFile != null) {
                _inStrategy = new FileInputStrategy();
            } else {
                if (_mode == Main.ExecutionMode.INTERACTIVE || _mode == Main.ExecutionMode.WINDOW) {
                    _inStrategy = new NullInputStrategy();
                } else {
                    _inStrategy = new ConsoleInputStrategy();
                }
            }

            return new WindowIn(_inStrategy);
        }

        private OutStrategy getOutputStrategy() {
            if (_outFile != null) {
                _outStrategy = new FileWriteStrategy(_outFile);
            } else {
                if (_mode == Main.ExecutionMode.INTERACTIVE || _mode == Main.ExecutionMode.WINDOW) {
                    _outStrategy = new NullWriteStrategy();
                } else {
                    _outStrategy = new ConsoleWriteStrategy();
                }
            }

            return new WindowOut(_outStrategy);
        }

        private ExecutionMode getMode() {
            return _mode;
        }

        private String getAsm() {
            return _asmFile;
        }

        private String getIn() {
            return _inFile;
        }

        private String getOut() {
            return _outFile;
        }

        private boolean getLogOption() {
        	return _writeLog;
        }
    }
}
