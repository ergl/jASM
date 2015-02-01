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


/**
 * Ejecuta las instrucciones especificadas por el usuario.
 * Llama a sus observadores cuando se realiza una acción. Ver CPUMessage
 * 
 * @author Borja
 * @author Chaymae
 */
public class CPU extends Watchable {	

    private static final String NUM_ERROR =     "El valor introducido debe ser un número";
    private static final String FORMAT_ERROR =  "La posición no puede ser negativa";
    
    private Memory memory;
    private OperandStack stack;
    private ExecutionManager executionManager;
    private ProgramMV program;
    private InStrategy inStr;
    private OutStrategy outStr;

    public CPU(InStrategy in, OutStrategy out) {
        this.memory = 			new Memory();
        this.stack = 			new OperandStack();
        this.executionManager = new ExecutionManager();
        this.inStr = 			in;
        this.outStr = 			out;
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
        
        if(!this.isHalted()) {
            if (inst != null) {
                try {
                    inst.execute(executionManager, memory, stack, inStr, outStr);
                    this.executionManager.onNextInstruction();
                } catch(RecoverableException re) {
                    this.notifyViews(re.getMessage());
                    throw re;
                } catch(UnrecoverableException ue) {
                    stop();
                    this.notifyViews(ue.getMessage());
                }
            }
            else {
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
            step();
            while (!this.isHalted())
                step();
        } catch (RecoverableException e) {}
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
        if(param == null)
            cpuDebug(new Pop());
        
        else {
            if(param1 == null) {
                if(Commons.isInteger(param))
                    cpuDebug(new Push((Integer.parseInt(param))));
                else {
                    this.setChanged();
                    this.notifyViews(NUM_ERROR);
                }
            }
            else {
                if(!Commons.isInteger(param) || !Commons.isInteger(param1)) {
                    this.setChanged();
                    this.notifyViews(NUM_ERROR);
                    return;
                }
                if(Integer.parseInt(param) < 0) {
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
            inst.execute(executionManager, memory, stack, null, null);
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
        if (inst == null)
            stop();
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
    
    public void deleteAsignedWatchers() {
        this.deleteWatchers();
        this.executionManager.deleteWatchers();
        this.stack.deleteWatchers();
        this.memory.deleteWatchers();
    }

    /**
     * Devuelve una version imprimible del estado de la maquina.
     * Muestra el estado de la pila de operandos y de la memoria.
     */
    @Override
    public String toString() {
        return System.lineSeparator() + memory.toString() + System.lineSeparator() + stack.toString();
    }
}
