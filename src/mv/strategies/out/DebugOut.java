package mv.strategies.out;

import mv.strategies.OutStrategy;

public class DebugOut implements OutStrategy {

    private OutStrategy outStr;

    public DebugOut(OutStrategy _outStr) {
        this.outStr = _outStr;
    }

    @Override
    public void open() {
        outStr.open();
    }

    @Override
    public void write(char c) {
        outStr.write(c);

        // TODO Auto-generated method stub

    }

    @Override
    public void close() {
        outStr.close();
    }
}
