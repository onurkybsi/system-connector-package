package nl.tue.systemconnectorpackage.clients.services.systemInformation.models;

import nl.tue.systemconnectorpackage.common.StringUtilities;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;

/**
 * Represents metadata information of a system which is connected to other by
 * system-connector
 */
public class SystemInformation {
    private String systemName;

    public SystemInformation(String systemName) {
        if (StringUtilities.isValid(systemName))
            throw new InvalidParameterException("systemName is not valid!");
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }
}
