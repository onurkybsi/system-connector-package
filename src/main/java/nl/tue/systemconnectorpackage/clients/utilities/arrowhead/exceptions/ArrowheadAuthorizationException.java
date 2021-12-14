package nl.tue.systemconnectorpackage.clients.utilities.arrowhead.exceptions;

/**
 * Represents exceptions occurred when communicate with Authorization system
 */
public class ArrowheadAuthorizationException extends ArrowheadException {
    public ArrowheadAuthorizationException(String errorMessage) {
        super(errorMessage);
    }
}