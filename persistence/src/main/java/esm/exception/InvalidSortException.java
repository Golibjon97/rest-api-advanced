package esm.exception;

public class InvalidSortException extends RuntimeException{
    public InvalidSortException() {
        super("Invalid sort type");
    }
}
