package mv.cpu;

/**
 * Individual Memory Cell
 * Each cell has a value and a reference (memory address)
 *
 * @author Borja
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
