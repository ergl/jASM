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
 * Main component of the VM. It executes the instructions and commands given by the user.
 * Each change is broadcasted to its observers
 *
 * @author Borja
 */
public class CPU extends Watchable {

    private static final String SHOW_STATUS = "CPU state after step: ";
    private static final String INST_MSG_BEGIN = "Executing: ";

    private static final String ERR_NUM = "Error: Input must be a number";
    private static final String ERR_FORMAT = "Error: Value can't be negative";

    private static final String LOG_FILE = "status.log";

    private File logFile;
    private Path logFilePath;
    private PrintWriter logWriter;

    private Memory memory;
    private OperandStack stack;
    private ExecutionManager executionManager;
    private ProgramMV program;
    private InStrategy inStr; //TODO: Check if strategies are needed
    private OutStrategy outStr;
    private RegisterBank registerList;

    private boolean writeLog;


    public CPU(InStrategy in, OutStrategy out, boolean writeLog) {
        this.memory = new Memory();
        this.stack = new OperandStack();
        this.executionManager = new ExecutionManager();
        this.registerList = new RegisterBank();
        setLogOption(writeLog);
    }

    private void setLogOption(boolean writeLog) {
        if (this.writeLog = writeLog) {
            configureLog();
        }
    }

    private void configureLog() {
        logFilePath = Paths.get(LOG_FILE);

        if (Files.exists(logFilePath)) {
            try {
                logWriter = new PrintWriter(new FileWriter(logFilePath.toFile()));
                logFile = logFilePath.toFile();
            } catch (IOException e) {
                System.exit(2); //TODO
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

    public void reset() {
        memory.flush();
        stack.flush();
        executionManager.reset();
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
     * Loads the program that will be executed
     *
     * @param program Program object to be executed
     */
    public void loadProgram(ProgramMV program) {
        this.program = program;
        this.executionManager.configureExecutionManager(this.program.getCont());
    }

    /**
     * Executes a single Instruction, to be determined by the program counter
     *
     * @throws commons.exceptions.RecoverableException //TODO
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
                System.err.println("Error: No instruction");
                stop();
            }
        }
    }

    /**
     * Runs the user program
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
     * Executes a debug instruction on the CPU
     *
     * @param param param 0 of the instruction to be executed
     * @param param1 param 1 of the instruction to be executed
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
                    this.notifyViews(ERR_NUM);
                }
            } else {
                if (!Commons.isInteger(param) || !Commons.isInteger(param1)) {
                    this.setChanged();
                    this.notifyViews(ERR_NUM);
                    return;
                }

                if (Integer.parseInt(param) < 0) {
                    this.setChanged();
                    this.notifyViews(ERR_FORMAT);
                    return;
                }

                cpuDebug(new Store(Integer.parseInt(param), Integer.parseInt(param1)));
            }
        }
    }

    /**
     * Allows the user to debug the CPU
     *
     * @param inst Debug instruction to be executed
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
     * Get the next instruction to be executed, determined by the program counter
     *
     * @return The next Instruction to be executed. If there are no more instructions, shuts down the CPU //TODO: shouldn't be this way
     */
    public Instruction nextInstruction() {
        Instruction inst = this.program.getInstructionAt(this.executionManager.getPc());
        if (inst == null) {
            stop();
        }
        return inst;
    }

    /**
     * Shuts down the CPU
     */
    public void stop() {
        this.executionManager.stop();

    }

    /**
     * Is the CPU off?
     *
     * @return CPU state
     */
    public boolean isHalted() {
        return this.executionManager.isHalted();
    }

    // Displays the ASM source code to be executed
    public String printProgram() {
        return this.program.toString();
    }

    // TODO: Diff between print and showProgram?
    public String[] showProgram() {
        return this.program.displayContent();
    }

    // Add observer to the Execution Manager
    public void addEMWatcher(Watcher w) {
        this.executionManager.addWatcher(w);
    }

    // Add observer to the Stack
    public void addStackWatcher(Watcher w) {
        this.stack.addWatcher(w);
    }

    // Add observer to the Memory
    public void addMemoryWatcher(Watcher w) {
        this.memory.addWatcher(w);
    }

    // Add observer to the Register list
    public void addRegisterWatcher(Watcher w) {
        this.registerList.addWatcher(w);
    }

    // Deletes all observers and closes any open resources
    public void deleteAssignedWatchers() {
        this.deleteWatchers();
        this.executionManager.deleteWatchers();
        this.stack.deleteWatchers();
        this.memory.deleteWatchers();
        this.registerList.deleteWatchers();

        closeResources();
    }

    /**
     * Prints CPU status after instruction execution
     *
     * @param instruction Instructions that has been executed
     *
     * @return CPU status info
     */
    public String printStatus(String instruction) {
        return INST_MSG_BEGIN + instruction + System.lineSeparator() + SHOW_STATUS + this.toString();
    }

    /**
     * @return Prints Memory, Stack and Register list status
     */
    @Override
    public String toString() {
        return System.lineSeparator() + memory.toString() +
                System.lineSeparator() + stack.toString() +
                System.lineSeparator() + registerList.toString();
    }
}
