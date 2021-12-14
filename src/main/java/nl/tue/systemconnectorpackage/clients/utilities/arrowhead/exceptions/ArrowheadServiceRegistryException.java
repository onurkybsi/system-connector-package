package nl.tue.systemconnectorpackage.clients.utilities.arrowhead.exceptions;

/**
 * Represents exceptions occurred when communicate with ServiceRegistry system
 */
public class ArrowheadServiceRegistryException extends ArrowheadException {
    public ArrowheadServiceRegistryException(String errorMessage) {
        super(errorMessage);
    }
}