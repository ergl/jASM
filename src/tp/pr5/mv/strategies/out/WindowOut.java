package tp.pr5.mv.strategies.out;

import tp.pr5.commons.watcherPattern.Watchable;
import tp.pr5.mv.strategies.OutStrategy;

public class WindowOut extends Watchable implements OutStrategy {

    private OutStrategy outStr;

    public WindowOut(OutStrategy _outStr) {
        this.outStr = _outStr;
    }

    @Override
    public void open() {
        outStr.open();
    }

    @Override
    public void write(char c) {
        this.setChanged();
        this.notifyViews(c);

        outStr.write(c);
    }

    @Override
    public void close() {
        outStr.close();
    }
}
