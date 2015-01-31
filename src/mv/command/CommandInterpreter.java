package tp.pr5.mv.command;

import tp.pr5.commons.exceptions.RecoverableException;
import tp.pr5.commons.exceptions.UnrecoverableException;
import tp.pr5.mv.cpu.CPU;

/**
 * Clase abstracta común a todos los comandos de debug de la VM.
 * 
 * @author Borja
 * @author Chaymae
 */
public abstract class CommandInterpreter {
    protected static final String INST_MSG_BEGIN = "Comienza la ejecución de: ";
    protected static final String EXEC_END = "El estado de la máquina tras ejecutar la instrucción es: ";


    protected static CPU cpu;

    /**
     * Establece la cpu sobre la que actuarán los diversos comandos.
     */
    public static void configureCommandInterpreter(CPU _cpu) {
        cpu = _cpu;
    }

    /**
     * Analiza el input de un usuario y lo transforma en comando.
     * 
     * @param input input del usuario
     * @return objeto comando correctamente formateado
     */
    public abstract CommandInterpreter parseComm(String input);

    /**
     * Método encargado de ejecutar el comando sobre la CPU.
     * @return 
     * @throws UnrecoverableException TODO
     * @throws RecoverableException TODO
     */
    public abstract void executeCommand(CPU cpu) throws RecoverableException;

    /**
     * Método que determina si la CPU está apagada.
     * 
     * @return el estado de la CPU
     */
    public static boolean isQuit() {
        return cpu.isHalted();
    }
}
