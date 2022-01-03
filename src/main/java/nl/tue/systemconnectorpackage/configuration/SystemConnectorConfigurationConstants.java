package nl.tue.systemconnectorpackage.configuration;

/**
 * Contains constant values for system-connector-package clients' configurations
 */
public class SystemConnectorConfigurationConstants {
    private SystemConnectorConfigurationConstants() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Package name to be scanned by Spring to register dependencies of
     * system-connector-package
     */
    public static final String PACKAGE_NAME_TO_BE_SCANNED_FOR_CONFIGURATION = "nl.tue";
}