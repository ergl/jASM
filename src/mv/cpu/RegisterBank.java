package mv.cpu;

import commons.watcherPattern.Watchable;

import java.util.ArrayList;
import java.util.ListIterator;

public class RegisterBank extends Watchable {

    private static int MAX_REGISTER = 4;
    private ArrayList<Registro> registerList;

    public RegisterBank() {
        this.registerList = new ArrayList<Registro>();
        init();
    }

    private void init() {
        for(int i = 0; i < MAX_REGISTER; i++) {
            registerList.add(new Registro(0));
        }
    }

    public void store(int pos, int value) {
        this.registerList.get(pos).setContent(value);
        this.setChanged();
        this.notifyViews(displayContent());
    }

    public int load(int pos) {
        return this.registerList.get(pos).getContent();
    }

    private String displayContent() {
        ListIterator<Registro> li = registerList.listIterator();

        StringBuilder sb = new StringBuilder();
        while(li.hasNext()) {
            Registro n = li.next();
            sb.append(n.getContent() + " ");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        ListIterator<Registro> li = registerList.listIterator();

        StringBuilder sb = new StringBuilder();
        sb.append("Registros: ");

        while(li.hasNext()) {
            Registro n = li.next();
            sb.append("[R"+ registerList.indexOf(n) + "]: " + n + " ");
        }

        return sb.toString();
    }
}
