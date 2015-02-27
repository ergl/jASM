package ui;

import commons.watcherPattern.Watchable;
import commons.watcherPattern.Watcher;
import mv.Main;
import mv.Main.ExecutionMode;

import java.util.Scanner;

public class TextView implements Watcher {

    private Scanner sc;
    private ExecutionMode mode;
    private TextController controller;

    public TextView(TextController cont, Main.ExecutionMode mode) {
        this.mode = mode;
        this.controller = cont;
        this.sc = new Scanner(System.in);
    }

    public void enable() {
        controller.init(this);
        switch (mode) {
            case BATCH:
                doBatch();
                break;
            case INTERACTIVE:
                doInteractive();
                break;
            default:
                doBatch();
                break;
        }
    }

    void show(String message, boolean err) {
        if (err) {
            System.err.println(message);
        } else {
            System.out.println(message);
        }
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
        } while (!controller.isHalted());
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
