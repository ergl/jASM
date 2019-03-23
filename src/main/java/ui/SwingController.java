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

package ui;

import commons.exceptions.RecoverableException;
import commons.watcherPattern.Watchable;
import commons.watcherPattern.Watcher;
import mv.cpu.CPU;
import mv.cpu.ProgramMV;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

public class SwingController {

    private CPU cpu;
    private ProgramMV program;

    private InStrategy inStr;
    private OutStrategy outStr;

    private SwingView swingView;

    public SwingController(CPU cpu, ProgramMV program, InStrategy inStr, OutStrategy outStr) {
        this.cpu = cpu;
        this.program = program;
        this.inStr = inStr;
        this.outStr = outStr;
    }

    public void addView(SwingView view) {
        this.swingView = view;
    }

    void init(Watcher w) {
        cpu.addWatcher(w);
        char[] inFileContents;

        inFileContents = this.inStr.showFile();
        cpu.loadProgram(program);
        this.swingView.init(inFileContents, cpu.showProgram());
    }

    void addStrWatcher(Watcher inStrWatcher, Watcher outStrWatcher) {
        ((Watchable) inStr).addWatcher(inStrWatcher);
        ((Watchable) outStr).addWatcher(outStrWatcher);
    }

    void addCpuWatchers(Watcher programWatcher, Watcher stackWatcher,
    		Watcher memoryWatcher, Watcher registerWatcher) {
        
    	cpu.addEMWatcher(programWatcher);
        cpu.addStackWatcher(stackWatcher);
        cpu.addMemoryWatcher(memoryWatcher);
        cpu.addRegisterWatcher(registerWatcher);
    }

    /**
     * Hard reset: Resets the VM and all associated files.
     *
     * Difference between this and the Reset command is that the later only resets the VM
     */
    void reset() {
        inStr.close();
        outStr.close();
        inStr.open(inStr.getFilePath());
        outStr.open();
        cpu.reset();
    }

    void stepEvent() {
        if (isReady()) {
            try {
                cpu.step();
            } catch (RecoverableException e) {
                // Already handled by view
            }
        }
    }

    void quitEvent() {
        this.swingView.quit();
    }

    void pushEvent(String value) {
        if (isReady()) {
            cpu.debugPush(value);
        }
    }

    void popEvent() {
        if (isReady()) {
            cpu.debugPop();
        }
    }

    void writeEvent(String position, String value) {
        if (isReady()) {
            cpu.debugWrite(position, value);
        }
    }

    boolean ready() {
        return !cpu.isHalted();
    }

    private boolean isReady() {
        if (!ready()) {
            swingView.disableActions();
            return false;
        }
        return true;
    }

    void shutdown() {
        try {
            this.cpu.stop();
            this.inStr.close();
            this.outStr.close();
            this.cpu.deleteAssignedWatchers();
        } catch (NullPointerException e) {
            // We are exiting the program anyway
        }
    }
}
