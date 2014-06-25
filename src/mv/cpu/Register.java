package mv.cpu;

public class Register {

    private int value;

    public Register(int _content) {
        this.value = _content;
    }

    public void setContent(int _content) {
        this.value = _content;
    }

    public int getContent() {
        return this.value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
