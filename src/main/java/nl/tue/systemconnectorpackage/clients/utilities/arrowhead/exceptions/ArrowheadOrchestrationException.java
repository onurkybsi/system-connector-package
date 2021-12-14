package nl.tue.systemconnectorpackage.clients.utilities.arrowhead.exceptions;

/**
 * Represents exceptions occurred when communicate with Orchestration system
 */
public class ArrowheadOrchestrationException extends ArrowheadException {
    public ArrowheadOrchestrationException(String errorMessage) {
        super(errorMessage);
    }
}