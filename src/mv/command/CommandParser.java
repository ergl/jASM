package mv.command;

import mv.command.commandList.*;

/**
 * Clase encargada de generar comandos a partir del input del usuario.
 *
 * @author Borja
 * @author Chaymae
 */
public class CommandParser {

    /**
     * Array que contiene todos los comandos disponibles al usuario.
     */
    private static CommandInterpreter[] commands = {new Step(), new Steps(), new Run(), new Quit(), new DebugPush(), new DebugPop(), new DebugWrite()};

    /**
     * Devuelve un objeto CommandInterpreter que podra ser procesado por la cpu.
     * El input debe estar correctamente formateado o de lo contrario ignorara
     * la orden y se interpretara como un error.
     *
     * @param input string describiendo un comando
     *
     * @return comando correctamente formateado
     */
    public static CommandInterpreter parseCommand(String input) {
        for (CommandInterpreter comm : commands) {
            CommandInterpreter tmp = comm.parseComm(input);
            if (tmp != null) {
                return tmp;
            }
        }
        return null;
    }
}
