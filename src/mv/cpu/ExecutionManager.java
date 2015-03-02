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

import commons.watcherPattern.Watchable;

/**
 * CPU control unit
 * Executes all instructions related with the program counter or CPU state
 *
 * @author Borja
 */
public class ExecutionManager extends Watchable {

    private int nextPc;
    private int currentPc;
    private int programCont;
    private boolean halt;

    public ExecutionManager() {
        this.currentPc = 0;
        this.nextPc = this.currentPc + 1;
        this.halt = false;
    }

    // Reset the program counter
    void reset() {
        currentPc = 0;
        nextPc = currentPc + 1;
        halt = false;
        this.setChanged();
        this.notifyViews(currentPc);
    }

    /**
     * Sets the number of instructions on the program
     *
     * @param programCont instruction counter
     */
    public void configureExecutionManager(int programCont) {
        this.programCont = programCont;
    }

    public int getPc() {
        return this.currentPc;
    }

    public void setPc(int newPc) {
        if (!this.halt && newPc >= 0) {
            this.nextPc = newPc;
        }
    }

    public void incrementPc(int increment) {
        if (!this.halt && (this.nextPc + increment - 1 >= 0)) {
            this.nextPc += increment - 1;
        }
    }

    /**
     * Sets the program counter on the next instruction to be executed.
     * Will be called on each CPU step
     */
    public void onNextInstruction() {
        if (this.nextPc < this.programCont) {
            this.currentPc = this.nextPc;
            this.nextPc = this.currentPc + 1;

            this.setChanged();
            this.notifyViews(this.currentPc);
        } else {
            this.halt = true;
        }
    }

    public void stop() {
        this.halt = true;
    }

    public boolean isHalted() {
        return this.halt;
    }
}
