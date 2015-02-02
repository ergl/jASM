package mv.cpu;

import commons.Commons;
import commons.exceptions.RecoverableException;
import commons.exceptions.UnrecoverableException;
import commons.exceptions.BreakpointException;
import commons.watcherPattern.Watchable;
import commons.watcherPattern.Watcher;
import mv.ins.Instruction;
import mv.ins.instList.stackModifiers.Push;
import mv.ins.instList.stackModifiers.Store;
import mv.ins.instList.summitModifiers.Pop;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Ejecuta las instrucciones especificadas por el usuario.
 * Llama a sus observadores cuando se realiza una acción. Ver CPUMessage
 *
 * @author Borja
 * @author Chaymae
 */
public class CPU extends Watchable {

    private static final String SHOW_STATUS = "El estado de la máquina tras ejecutar la instrucción es: ";
    private static final String INST_MSG_BEGIN = "Comienza la ejecución de: ";

    private static final String NUM_ERROR = "El valor introducido debe ser un número";
    private static final String FORMAT_ERROR = "La posición no puede ser negativa";

    private static final String LOG_FILE = "status.log";

    private Path logFilePath;
    private PrintWriter logWriter;
    private File logFile;

    private Memory memory;
    private OperandStack stack;
    private ExecutionManager executionManager;
    private ProgramMV program;
    private InStrategy inStr;
    private OutStrategy outStr;
    private RegisterBank registerList;

    private boolean writeLog;


    public CPU(InStrategy _in, OutStrategy _out, boolean _writeLog) {
        this.memory = new Memory();
        this.stack = new OperandStack();
        this.executionManager = new ExecutionManager();
        this.registerList = new RegisterBank();
        setLogOption(_writeLog);
    }

    private void setLogOption(boolean _writeLog) {
        this.writeLog = _writeLog;
        if (writeLog) {
            configureLog();
        }
    }

    public void reset() {
        memory.flush();
        stack.flush();
        executionManager.reset();
    }

    private void configureLog() {
        logFilePath = Paths.get(LOG_FILE);

        if (Files.exists(logFilePath)) {
            try {
                logWriter = new PrintWriter(new FileWriter(logFilePath.toFile()));
                logFile = logFilePath.toFile();
            } catch (IOException e) {
                System.exit(2);
            }
        } else {
            logFile = new File(LOG_FILE);
            try {
                logWriter = new PrintWriter(new FileWriter(logFile));
            } catch (IOException e) {
                System.exit(2);
            }
        }
    }

    private void log(String instruction) {
        logWriter.write(printStatus(instruction) + "\n\n");
    }

    private void closeResources() {
        logWriter.close();
    }

    public boolean breakpointsEnabled() {
        return this.executionManager.breakpointsEnabled();
    }

    public void enableBreakpoints() {
        this.executionManager.enableBreakpoints();
    }

    public void disableBreakpoints() {
        this.executionManager.disableBreakpoints();
    }

    public void setBreakpoint(Integer i) {
        this.executionManager.addBreakpoint(i);
    }

    public void deleteBreakpoint(Integer i) {
        this.executionManager.deleteBreakpoint(i);
    }

    public void deleteBreakpoints() {
        this.executionManager.disableBreakpoints();
    }

    /**
     * Inicializa el programa a ejecutar por la CPU.
     *
     * @param program el programa a ejecutar por la CPU
     */
    public void loadProgram(ProgramMV program) {
        this.program = program;
        this.executionManager.configureExecutionManager(this.program.getCont());
    }

    /**
     * Ejecuta una de las instrucciones del programa.
     * Necesita que la VM esté encendida y que exista un programa válido.
     * La instrucción a ejecutar viene determinada por el contador de programa.
     */
    public void step() throws RecoverableException {
        int pc = executionManager.getPc();
        Instruction inst = this.program.getInstructionAt(pc);

        this.setChanged();

        if (!this.isHalted()) {
            if (inst != null) {
                try {
                    if (executionManager.breakpointsEnabled() && executionManager.onBreakpoint()) {
                        throw new BreakpointException("Breakpoint reached");
                    }
                    inst.execute(executionManager, memory, stack, inStr, outStr, registerList);
                    if (writeLog) {
                        log(inst.toString());
                    }
                    this.executionManager.onNextInstruction();
                } catch (RecoverableException re) {
                    this.notifyViews(re.getMessage());
                    throw re;
                } catch (UnrecoverableException ue) {
                    stop();
                    this.notifyViews(ue.getMessage());
                } catch (BreakpointException be) {
                    this.notifyViews(be.getMessage());
                }
            } else {
                System.err.println("No existe instrucción");
                stop();
            }
        }
    }

    /**
     * Ejecuta todas las instrucciones del programa.
     */
    public void runProgram() {
        try {
            if (!executionManager.breakpointsEnabled() && !executionManager.onBreakpoint()) {
                step();
            }
            while (!this.isHalted() && !executionManager.breakpointsEnabled() && !executionManager.onBreakpoint())
                step();
        } catch (RecoverableException e) {
            // TODO
        }
    }

    /**
     * Permite el debug de la CPU.
     * Permite al usuario realizar una operación sobre la pila de operandos
     * o la memoria desde la interfaz de debug del programa.
     *
     * @param param - Parámetro 0 de la instrucción a ejecutar
     * @param param1 - Parámetro 1 de la instrucción a ejecutar
     */
    public void debugInstruction(String param, String param1) {
        if (param == null) {
            cpuDebug(new Pop());
        } else {
            if (param1 == null) {
                if (Commons.isInteger(param)) {
                    cpuDebug(new Push((Integer.parseInt(param))));
                } else {
                    this.setChanged();
                    this.notifyViews(NUM_ERROR);
                }
            } else {
                if (!Commons.isInteger(param) || !Commons.isInteger(param1)) {
                    this.setChanged();
                    this.notifyViews(NUM_ERROR);
                    return;
                }

                if (Integer.parseInt(param) < 0) {
                    this.setChanged();
                    this.notifyViews(FORMAT_ERROR);
                    return;
                }

                cpuDebug(new Store(Integer.parseInt(param), Integer.parseInt(param1)));
            }
        }
    }

    /**
     * Permite el debug de la CPU.
     * Permite al usuario realizar una operación sobre la pila de operandos
     * o la memoria desde la interfaz de debug del programa.
     *
     * @param inst Instrucción a ejecutar sobre la pila
     */
    private void cpuDebug(Instruction inst) {
        this.setChanged();
        try {
            inst.execute(executionManager, memory, stack, null, null, registerList);
        } catch (RecoverableException re) {
            this.notifyViews(re.getMessage());
        } catch (UnrecoverableException ue) {
            this.notifyViews(ue.getMessage());
        }
    }

    /**
     * Permite conocer la siguiente operación a ejecutar.
     * Ésta viene determinada por el contador de programa en ese momento.
     *
     * @return La operación delimitada por el contador de programa. En caso de que no exista devolverá null
     */
    public Instruction nextInstruction() {
        Instruction inst = this.program.getInstructionAt(this.executionManager.getPc());
        if (inst == null) {
            stop();
        }
        return inst;
    }

    /**
     * Apaga la CPU.
     */
    public void stop() {
        this.executionManager.stop();

    }

    /**
     * Devuelve el estado de la CPU.
     *
     * @return si la CPU se encuentra encendida o apagada
     */
    public boolean isHalted() {
        return this.executionManager.isHalted();
    }

    public String printProgram() {
        return this.program.toString();
    }

    public String[] showProgram() {
        return this.program.displayContent();
    }

    public void addEMWatcher(Watcher w) {
        this.executionManager.addWatcher(w);
    }

    public void addStackWatcher(Watcher w) {
        this.stack.addWatcher(w);
    }

    public void addMemoryWatcher(Watcher w) {
        this.memory.addWatcher(w);
    }

    public void addRegisterWatcher(Watcher w) {
        this.registerList.addWatcher(w);
    }

    public void deleteAsignedWatchers() {
        this.deleteWatchers();
        this.executionManager.deleteWatchers();
        this.stack.deleteWatchers();
        this.memory.deleteWatchers();
        this.registerList.deleteWatchers();

        closeResources();
    }

    public String printStatus(String instruction) {
        return INST_MSG_BEGIN + instruction + System.lineSeparator() + SHOW_STATUS + this.toString();
    }

    /**
     * Devuelve una version imprimible del estado de la maquina.
     * Muestra el estado de la pila de operandos y de la memoria.
     */
    @Override
    public String toString() {
        return System.lineSeparator() + memory.toString() +
                System.lineSeparator() + stack.toString() +
                System.lineSeparator() + registerList.toString();
    }
}
