package mv.cpu;

import commons.watcherPattern.Watchable;

import java.util.ArrayList;
import java.util.ListIterator;

public class RegisterBank extends Watchable {

    private static int MAX_REGISTER = 4;
    private ArrayList<Register> registerList;

    public RegisterBank() {
        this.registerList = new ArrayList<Register>();
        init();
    }

    private void init() {
        for (int i = 0; i < MAX_REGISTER; i++) {
            registerList.add(new Register(0));
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
        ListIterator<Register> li = registerList.listIterator();

        StringBuilder sb = new StringBuilder();
        while (li.hasNext()) {
            Register n = li.next();
            sb.append(n.getContent()).append(" ");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        ListIterator<Register> li = registerList.listIterator();

        StringBuilder sb = new StringBuilder();
        sb.append("Register: ");

        while (li.hasNext()) {
            Register n = li.next();
            sb.append("[R").append(registerList.indexOf(n)).append("]: ").append(n).append(" ");
        }

        return sb.toString();
    }
}
