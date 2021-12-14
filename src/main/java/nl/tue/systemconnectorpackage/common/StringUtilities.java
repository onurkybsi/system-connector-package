package nl.tue.systemconnectorpackage.common;

/**
 * Provides various functionalities for string value operations
 */
public class StringUtilities {
    private StringUtilities() {
        throw new IllegalStateException("This is an utility class!");
    }

    /**
     * Checks string value is not null and empty
     *
     * @param value String value to validate
     * @return True if it's not null or empty
     */
    public static boolean isValid(String value) {
        if (value == null)
            return false;
        if (value.trim().length() == 0)
            return false;
        return true;
    }
}
