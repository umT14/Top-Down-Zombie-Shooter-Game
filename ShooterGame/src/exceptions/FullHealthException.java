package exceptions;

public class FullHealthException extends Exception {

    public FullHealthException() {
        super("Player health is already max. No need to use medicals");
    }
}
