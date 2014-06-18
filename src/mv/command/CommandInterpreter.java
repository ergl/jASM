package mv.command;

import commons.exceptions.RecoverableException;
import mv.cpu.CPU;

/**
 * Clase abstracta común a todos los comandos de debug de la VM.
 *
 * @author Borja
 * @author Chaymae
 */
public abstract class CommandInterpreter {
	
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
     *
     * @return objeto comando correctamente formateado
     */
    public abstract CommandInterpreter parseComm(String input);

    /**
     * Método encargado de ejecutar el comando sobre la CPU.
     *
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
