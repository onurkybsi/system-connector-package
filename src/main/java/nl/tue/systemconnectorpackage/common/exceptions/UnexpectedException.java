package nl.tue.systemconnectorpackage.common.exceptions;

/**
 * Represents exceptions occurred when exception is not expected
 */
public class UnexpectedException extends RuntimeException {
    public UnexpectedException() {
        super();
    }

    public UnexpectedException(String errorMessage) {
        super(errorMessage);
    }
}
