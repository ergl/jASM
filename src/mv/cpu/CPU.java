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

import commons.Commons;
import commons.exceptions.RecoverableException;
import commons.exceptions.UnrecoverableException;
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
 * Each change is broadcast to its observers
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
    private InStrategy inStr;
    private OutStrategy outStr;
    private RegisterBank registerList;

    private boolean writeLog;


    public CPU(InStrategy in, OutStrategy out, boolean writeLog) {
        this.inStr = in;
        this.outStr = out;

        this.memory = new Memory();
        this.stack = new OperandStack();
        this.registerList = new RegisterBank();
        this.executionManager = new ExecutionManager();

        configureLog(writeLog);
    }

    private void configureLog(boolean write) {

        if (!(writeLog = write)) {
            return;
        }

        logFilePath = Paths.get(LOG_FILE);

        if (Files.exists(logFilePath)) {
            try {
                logWriter = new PrintWriter(new FileWriter(logFilePath.toFile()));
                logFile = logFilePath.toFile();
            } catch (IOException e) {
                System.err.println("Couldn't create the log file. Skipping...");
                writeLog = false;
            }
        } else {
            logFile = new File(LOG_FILE);
            try {
                logWriter = new PrintWriter(new FileWriter(logFile));
            } catch (IOException e) {
                System.err.println("Couldn't create the log file. Skipping...");
                writeLog = false;
            }
        }
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
            while (!this.isHalted()) {
                step();
            }
        } catch (RecoverableException e) {
            // TODO: ?
        }
    }

    /**
     * Executes push on the CPU
     *
     * @param param value to be stacked
     */
    public void debugPush(String param) {
        if (Commons.isInteger(param)) {
            cpuDebug(new Push(Integer.parseInt(param)));
        } else {
            this.setChanged();
            this.notifyViews(ERR_NUM);
        }
    }

    /**
     * Executes pop on the CPU
     */
    public void debugPop() {
        cpuDebug(new Pop());
    }

    /**
     * Executes store on the CPU
     *
     * @param position memory position in which we will write
     * @param value value to be written to memory
     */
    public void debugWrite(String position, String value) {
        if (!Commons.isInteger(position) || !Commons.isInteger(value)) {
            this.setChanged();
            this.notifyViews(ERR_NUM);
            return;
        }

        if (Integer.parseInt(position) < 0) {
            this.setChanged();
            this.notifyViews(ERR_FORMAT);
            return;
        }

        cpuDebug(new Store(Integer.parseInt(position), Integer.parseInt(value)));
    }

    /**
     * Allows the user to debug the CPU
     *
     * @param inst Debug instruction to be executed
     */
    private void cpuDebug(Instruction inst) {
        this.setChanged();
        try {
            inst.execute(executionManager, memory, stack, inStr, outStr, registerList);
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
     * Resets the CPU to its initial state
     */
    public void reset() {
        memory.flush();
        stack.flush();
        executionManager.reset();
    }

    /**
     * Is the CPU off?
     *
     * @return CPU state
     */
    public boolean isHalted() {
        return this.executionManager.isHalted();
    }

    // Displays the ASM source code to be executed; consumed by the text view
    public String printProgram() {
        return this.program.toString();
    }

    // Displays the program in an array: to be consumed by the swing view
    public String[] showProgram() {
        return this.program.displayContent();
    }

    private void log(String instruction) {
        logWriter.write(printStatus(instruction) + "\n\n");
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
        this.stack.deleteWatchers();
        this.memory.deleteWatchers();
        this.registerList.deleteWatchers();
        this.executionManager.deleteWatchers();

        closeResources();
    }

    private void closeResources() {
        logWriter.close();
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
