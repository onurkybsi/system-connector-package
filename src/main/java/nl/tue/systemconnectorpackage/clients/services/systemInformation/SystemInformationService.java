package nl.tue.systemconnectorpackage.clients.services.systemInformation;

import nl.tue.systemconnectorpackage.clients.services.systemInformation.models.SystemInformation;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;

/**
 * Contains functionalities for information of connected systems
 */
public interface SystemInformationService {
    /**
     * Receives system information by given system name
     *
     * @param systemName System name to get
     * @return System information
     */
    SystemInformation getSystemInformationByName(String systemName) throws InvalidParameterException;

    /**
     * Checks whether a system exists managed by system-connector by the given system name
     *
     * @param systemName System name to check
     * @return True if the system exists or false
     */
    boolean checkSystemIsExistByName(String systemName) throws InvalidParameterException;
}
