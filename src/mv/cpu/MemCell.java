package mv.cpu;

/**
 * Unidad individual de la Memoria.
 * La celula de memoria se compone de un valor y una referencia asociada
 * que podria compararse a su direccion de memoria.
 *
 * @author Borja
 * @author Chaymae
 */
public class MemCell {

    private int ref;
    private int val;

    public MemCell(int val, int ref) {
        this.ref = ref;
        this.val = val;
    }

    public void setVal(int value) {
        this.val = value;
    }

    public int getVal() {
        return this.val;
    }

    public int getPos() {
        return this.ref;
    }
}
