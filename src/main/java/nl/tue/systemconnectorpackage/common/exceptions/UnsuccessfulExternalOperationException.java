package nl.tue.systemconnectorpackage.common.exceptions;

/**
 * Represents exceptions occurred when the response from external service is not as expected
 */
public class UnsuccessfulExternalOperationException extends RuntimeException {
    public UnsuccessfulExternalOperationException() {
        super();
    }

    public UnsuccessfulExternalOperationException(String errorMessage) {
        super(errorMessage);
    }
}
