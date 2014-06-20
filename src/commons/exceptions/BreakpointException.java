package commons.exceptions;

public class BreakpointException extends Exception {

    public BreakpointException(String message) {
        super(message);
    }
    
    public String getMessage() {
        return super.getMessage();
    }
}
