package mv.strategies.in;

import commons.watcherPattern.Watchable;
import mv.strategies.InStrategy;

import java.nio.file.Path;

public class WindowIn extends Watchable implements InStrategy {

    private InStrategy inStr;

    public WindowIn(InStrategy _inStr) {
        this.inStr = _inStr;
    }

    @Override
    public void open(Path filePath) {
        inStr.open(filePath);
    }

    @Override
    public int read() {
        int value = this.inStr.read();

        this.setChanged();
        this.notifyViews(value);

        return value;
    }

    @Override
    public void close() {
        this.inStr.close();
    }

    @Override
    public char[] showFile() {
        return inStr.showFile();
    }

    @Override
    public Path getFilePath() {
        return inStr.getFilePath();
    }
}
