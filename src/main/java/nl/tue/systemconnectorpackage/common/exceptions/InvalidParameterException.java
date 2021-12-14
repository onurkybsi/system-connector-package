package nl.tue.systemconnectorpackage.common.exceptions;

/**
 * Represents invalid parameter for a function
 */
public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException() {
        super();
    }

    public InvalidParameterException(String errorMessage) {
        super(errorMessage);
    }
}
