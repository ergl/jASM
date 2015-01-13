package tp.pr5.mv.cpu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import tp.pr5.commons.watcherPattern.Watchable;

/**
 * La pila de operandos de la maquina virtual.
 * 
 * @author Borja
 * @author Chaymae
 */
public class OperandStack extends Watchable {

    private ArrayList<Integer> stack;

    public OperandStack() {
        stack = new ArrayList<>();
    }

    public void pushValue(Integer e) {
        stack.add(e);

        this.setChanged();
        this.notifyViews(this.displayContent());
    }

    public Integer popValue() {
        int value = stack.remove(elements() -1);

        this.setChanged();
        this.notifyViews(this.displayContent());

        return value;
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int elements() {
        return stack.size();
    }

    public String displayContent() {
        if(stack.size() == 0)
            return "";

        ListIterator<Integer> li = stack.listIterator(stack.size());
        
        StringBuilder sb = new StringBuilder();
        boolean firstValue = true;

        while(li.hasPrevious()) {
            Integer n = li.previous();
            if(firstValue) {
                sb.append(n);
                firstValue = false;
            }
            else
                sb.append("\n").append(n);
        }

        return sb.toString();
    }

    public String toString() {
        Iterator<Integer> it = stack.iterator();
        if (!it.hasNext())
            return "Pila de Operandos: <vacía>";

        StringBuilder sb  = new StringBuilder();
        sb.append("Pila de Operandos: ");
        
        do {
            sb.append(it.next());
            sb.append(' ');
        } while(it.hasNext());
        
        return sb.append("").toString();
    }
}
