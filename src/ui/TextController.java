package ui;

import commons.watcherPattern.Watcher;
import mv.command.CommandInterpreter;
import mv.command.CommandParser;
import mv.cpu.CPU;
import mv.cpu.ProgramMV;
import mv.strategies.InStrategy;
import mv.strategies.OutStrategy;

public class TextController {

    private static final String END_TOKEN = "QUIT";
    private static final String COMM_ERROR = "Error: Unknown command";

    private CPU cpu;
    private ProgramMV program;

    private boolean isHalted;

    private InStrategy inStr;
    private OutStrategy outStr;

    private CommandInterpreter debugCommand;

    private TextView textView;

    public TextController(CPU cpu, ProgramMV program, InStrategy inStr, OutStrategy outStr) {
        this.cpu = cpu;
        this.program = program;

        this.isHalted = false;

        this.inStr = inStr;
        this.outStr = outStr;

        CommandInterpreter.configure(cpu);
    }

    public void addView(TextView view) {
        this.textView = view;
    }

    void init(Watcher w) {
        cpu.addWatcher(w);
        cpu.loadProgram(program);
    }

    boolean isHalted() {
        return isHalted;
    }

    void debug(String input) {
        this.debugCommand = CommandParser.parseCommand(input);

        if (debugCommand == null) {
            textView.show(COMM_ERROR, true);
            return;
        }

        if (cpu.isHalted() || input.equalsIgnoreCase(END_TOKEN)) {
            isHalted = true;
            return;
        }

        try {
            debugCommand.executeCommand(cpu);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cpu.isHalted()) {
            isHalted = true;
        }
    }

    String showProgram() {
        return cpu.printProgram();
    }

    void runEvent() {
        cpu.runProgram();
        if (cpu.isHalted()) {
            textView.quit();
        }
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
