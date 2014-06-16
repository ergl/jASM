package tp.pr5.ui;

import java.util.Scanner;

import tp.pr5.commons.watcherPattern.Watchable;
import tp.pr5.commons.watcherPattern.Watcher;
import tp.pr5.mv.Main;
import tp.pr5.mv.Main.ExecutionMode;

public class TextView implements Watcher {
    
    private ExecutionMode mode;
    private TextController controller;
    private Scanner sc;
    
    public TextView(TextController _cont, Main.ExecutionMode _mode) {
        this.mode       = _mode;
        this.controller = _cont;
        this.sc         = new Scanner(System.in);
    }
    
    public void enable() {
        controller.init(this);
        switch(mode) {
            case BATCH:
                doBatch(); break;
            case INTERACTIVE:
                doInteractive(); break;
            case WINDOW:
                break;
            default:
                doBatch(); break;
        }        
    }

    void show(String message, boolean err) {
        if(err)
            System.err.println(message);
        else
            System.out.println(message);
    }

    private void doBatch() {
        controller.runEvent();
        quit();
    }
    
    private void doInteractive() {
        System.out.println(controller.showProgram());
        
        do {
            System.out.print("> ");
            controller.debug(sc.nextLine());
        } while(!controller.isHalted());
        
        quit();
    }
    
    void quit() {
        controller.shutdown();
        System.exit(0);
    }

    @Override
    public void updateDisplays(Watchable o, Object arg) {
        show((String) arg, true);
    }
}
