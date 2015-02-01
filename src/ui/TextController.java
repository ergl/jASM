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
    private static final String COMM_ERROR = "Error: Comando no reconocido";
    
    private CPU cpu;
    private ProgramMV program;

    private boolean isHalted;
    
    private InStrategy inStr;
    private OutStrategy outStr;

    private TextView textView;
    
    public TextController(CPU _cpu, ProgramMV _program, InStrategy _inStr, OutStrategy _outStr) {
        this.cpu = _cpu;
        this.program = _program;
        
        this.isHalted = false;
        
        this.inStr = _inStr;
        this.outStr = _outStr;
        
        CommandInterpreter.configureCommandInterpreter(cpu);
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
        CommandInterpreter debugCommand = CommandParser.parseCommand(input);
        
        if (debugCommand == null) {
            textView.show(COMM_ERROR, true);
            return;
        }
        
        if (cpu.isHalted() || input.equalsIgnoreCase(END_TOKEN))
            isHalted = true;
        
        try {
            debugCommand.executeCommand(cpu);
        } catch (Exception e) {}
        
        if (cpu.isHalted())
            isHalted = true;
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
    
    
    String showProgram() {
        return cpu.printProgram();
    }

    void runEvent() {
        cpu.runProgram();
        
        if(cpu.isHalted())
            textView.quit();
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
