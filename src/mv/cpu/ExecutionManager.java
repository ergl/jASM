package mv.cpu;

import commons.watcherPattern.Watchable;

import java.util.ArrayList;

/**
 * Unidad de control de la CPU.
 * Realiza todas las operaciones relacionadas con el contador de programa y el estado de la CPU.
 *
 * @author Borja
 * @author Chaymae
 */
public class ExecutionManager extends Watchable {

    private int currentPc;
    private int nextPc;
    private int programCont;
    private boolean halt;

    private ArrayList<Integer> breakpoints;
    private boolean enableBreakpoints;

    public ExecutionManager() {
        this.currentPc = 0;
        this.nextPc = this.currentPc + 1;
        this.halt = false;
        this.enableBreakpoints = true;
        this.breakpoints = new ArrayList<Integer>();
    }

    boolean onBreakpoint() {
        return breakpoints.contains(currentPc);
    }

    void addBreakpoint(Integer i) {
        if (!breakpoints.contains(i)) {
            breakpoints.add(i);
        }
    }

    void deleteBreakpoint(Integer i) {
        if (breakpoints.contains(i)) {
            breakpoints.remove(i);
        }
    }

    void clearBreakpoints() {
        breakpoints.clear();
    }

    void enableBreakpoints() {
        this.enableBreakpoints = true;
    }

    void disableBreakpoints() {
        this.enableBreakpoints = false;
    }

    boolean breakpointsEnabled() {
        return this.enableBreakpoints;
    }

    void reset() {
        currentPc = 0;
        nextPc = currentPc + 1;
        halt = false;
        this.setChanged();
        this.notifyViews(currentPc);
    }

    /**
     * Establece el número de instrucciones del programa.
     * <<<<<<< HEAD:src/mv/cpu/ExecutionManager.java
     * <p>
     * =======
     * <p>
     * <<<<<<< HEAD:src/mv/cpu/ExecutionManager.java
     * >>>>>>> af02449... Fixed bug preventing pausing/resetting program when pressing Run button multiple times. Changed package structure to new standard:src/mv/cpu/ExecutionManager.java
     * =======
     * >>>>>>> 2fdcfe6... Changed package hierarchy and fixed some code style issues:src/mv/cpu/ExecutionManager.java
     *
     * @param programCont el número de instrucciones a ejecutar
     */
    public void configureExecutionManager(int programCont) {
        this.programCont = programCont;
    }

    /**
     * Devuelve el contador de programa.
     *
     * @return el contador de programa
     */
    public int getPc() {
        return this.currentPc;
    }

    /**
     * Cambia el valor del contador de programa.
     * La CPU debe estár activa y el nuevo valor debe ser válido.
     * Éste método se ejecuta al realizar una operación Branch
     *
     * @param newPc el nuevo valor del contador de programa
     */
    public void setPc(int newPc) {
        if (!this.halt && newPc >= 0) {
            this.nextPc = newPc;
        }
    }

    /**
     * Aumenta el valor del contador de programa.
     * Éste método se ejecuta al realizar una operación RBranch
     *
     * @param increment la cantidad que debe aumentar el contador de programa
     */
    public void incrementPc(int increment) {
        if (!this.halt && (this.nextPc + increment - 1 >= 0)) {
            this.nextPc += increment - 1;
        }
    }

    /**
     * Sitúa el contador de programa en la siguiente operación a ejecutar.
     * Éste método se ejecuta con cada step de la CPU
     */
    public void onNextInstruction() {
        /*
        if(enableBreakpoints && breakpoints.contains(currentPc)) {
    		this.setChanged();
    		this.notifyViews(currentPc);
    		return;
    	}
    	*/
        if (this.nextPc < this.programCont) {
            this.currentPc = this.nextPc;
            this.nextPc = this.currentPc + 1;

            this.setChanged();
            this.notifyViews(this.currentPc);
        } else {
            this.halt = true;
        }
    }

    /**
     * Apaga la CPU y la VM
     */
    public void stop() {
        this.halt = true;
    }

    /**
     * Devuelve el estado de la CPU.
     *
     * @return el estado de la CPU
     */
    public boolean isHalted() {
        return this.halt;
    }
}
