package mv.cpu;

import commons.watcherPattern.Watchable;

/**
 * Memoria fisica de la maquina virtual.
 * Cien posiciones no ampliables, memoria ilimitada simulada por
 * un rango ilimitado de direcciones de memoria.
 * 
 * @author Borja
 * @author Chaymae
 */
public class Memory extends Watchable {

    private static final int MAX_MEMPOS = 100;

    private MemCell[] memArray;
    private int elements;

    public Memory() {
        this.memArray = new MemCell[MAX_MEMPOS];
        this.elements = 0;
    }

    public boolean isEmpty() {
        return elements == 0;
    }

    public boolean isFull() {
        return elements == MAX_MEMPOS;
    }

    public int loadValue(int pos) {
        return memArray[pos].getVal();
    }

    /**
     * Carga en la direccion de memoria dada un valor indicado.
     * Las inserciones se realizan de manera ordenada, por direccion de memoria,
     * y de menor a mayor.
     * <p>
     * Las direcciones son sobreescribibles, en cuyo caso solo se actualiza el valor.
     * 
     * @param val valor a cargar en memoria
     * @param ref direccion de memoria en la que sera cargado el valor
     */
    public boolean storeValue(int val, int ref) {
        boolean success = true;
        int oldRef = getMemoryReference(ref);

        this.setChanged();

        if (oldRef != -1) {
            memArray[oldRef].setVal(val);
            this.notifyViews(this.displayContent());
        }
        else if (!isFull()) {
            if (!isEmpty()) {
                int i = elements - 1;

                while (i >= 0 && (memArray[i].getPos() > ref)) {
                    memArray[i+1] = memArray[i];
                    i--;
                }

                memArray[i+1] = new MemCell(val, ref);
                elements++;

                this.notifyViews(this.displayContent());
            }

            else {
                memArray[elements] = new MemCell(val, ref);
                elements++;

                this.notifyViews(this.displayContent());
            }
        } else
            success =  false;

        return success;
    }

    /**
     * Devuelve la posicion en memoria de una direccion dada.
     * Devuelve -1 en caso de no existir la direccion especificada.
     * 
     * @param ref direccion de memoria que quiere situarse
     * @return posicion en memoria de la direccion
     * @see #busquedaBinariaRef(int, int, int)
     */
    public int getMemoryReference(int ref) {
        return busquedaBinariaRef(ref, 0, elements-1);
    }

    /**
     * Implementacion de la busqueda de inMemory.
     * 
     * @param ref direccion de memoria que quiere situarse
     * @param ini punto de inicio de la busqueda
     * @param fin punto de finalizacion de la busqueda
     * @return posicion de la direccion. Devuelve -1 si no existe
     */
    private int busquedaBinariaRef(int ref, int ini, int fin) {
        int pos = -1;

        if(ini > fin)
            return pos;

        else {
            pos = (ini + fin)/2;
            if(ref < this.memArray[pos].getPos() )
                return busquedaBinariaRef(ref, ini, pos-1);

            else if (ref  > this.memArray[pos].getPos() )	
                return busquedaBinariaRef(ref, pos+1, fin);

            else
                return pos;
        }
    }

    public String displayContent() {
        if(!isEmpty()) {
            String formattedMem = "";
            for(int i = 0; i < elements; i++) 
                formattedMem += memArray[i].getPos() + " " + memArray[i].getVal() + " "; 

            return formattedMem;
        }
        else return "";
    }

    @Override
    public String toString() {
        if(!isEmpty()) {
            String formattedMem = "";
            for(int i = 0; i < elements; i++)
                formattedMem += "[" + memArray[i].getPos()+ "]" + ":" + memArray[i].getVal() + " ";

            return "Memoria: " + formattedMem;
        }

        else return "Memoria: <vacía>";
    }
}
