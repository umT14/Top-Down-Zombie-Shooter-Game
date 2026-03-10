package exceptions;

public class EmptyHandException extends Exception {

    public EmptyHandException() {
        super("Player hand empty");
    }
}
