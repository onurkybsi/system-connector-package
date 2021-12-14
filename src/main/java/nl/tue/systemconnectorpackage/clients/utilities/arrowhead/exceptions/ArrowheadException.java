package nl.tue.systemconnectorpackage.clients.utilities.arrowhead.exceptions;

/**
 * Represents exceptions occurred during Arrowhead operations
 */
public class ArrowheadException extends RuntimeException {
    public ArrowheadException(String errorMessage) {
        super(errorMessage);
    }
}
