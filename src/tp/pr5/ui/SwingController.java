package tp.pr5.ui;

import tp.pr5.commons.exceptions.RecoverableException;
import tp.pr5.commons.watcherPattern.Watchable;
import tp.pr5.commons.watcherPattern.Watcher;
import tp.pr5.mv.cpu.CPU;
import tp.pr5.mv.cpu.ProgramMV;
import tp.pr5.mv.strategies.InStrategy;
import tp.pr5.mv.strategies.OutStrategy;


/**
 * Controlador del Vista Swing
 * Accede directamen a la CPU, y es observado por SwingView
 * 
 * @author Borja
 * @author Chaymae
 */
public class SwingController {

    private CPU cpu;
    private ProgramMV program;

    private InStrategy inStr;
    private OutStrategy outStr;

    private SwingView swingView;

    public SwingController(CPU _cpu, ProgramMV _program, InStrategy _inStr, OutStrategy _outStr) {
        this.cpu =      _cpu;
        this.program = 	_program;
        this.inStr = 	_inStr;
        this.outStr = 	_outStr;
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
    
    void addCpuWatchers(Watcher programWatcher, Watcher stackWatcher, Watcher memoryWatcher) {
        cpu.addEMWatcher(programWatcher);
        cpu.addStackWatcher(stackWatcher);
        cpu.addMemoryWatcher(memoryWatcher);
    }
        
    void stepEvent() {
        if(isReady()) {
            try {
                cpu.step();
            } catch (RecoverableException e) {/* Already handled by view */}
        }
    }

    void quitEvent() {
        this.swingView.quit();
    }

    void pushEvent(String value) {
        if(isReady())
            cpu.debugInstruction(value, null);
    }

    void popEvent() {
        if(isReady())
            cpu.debugInstruction(null, null);
    }

    void writeEvent(String position, String value) {
        if(isReady())
            cpu.debugInstruction(position, value);
    }

    boolean ready() {
        return !cpu.isHalted();
    }
    
    private boolean isReady() {
        if(!ready()) {
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
            this.cpu.deleteAsignedWatchers();
        } catch (NullPointerException e) { /* Ignore */ }
    }
}
