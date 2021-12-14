package nl.tue.systemconnectorpackage.common;

/**
 * Provides various functionalities to validate reference type values
 */
public class ValidationUtilities {
    private ValidationUtilities() {
        throw new IllegalStateException("This is an utility class!");
    }

    /**
     * Checks one of given reference values is null or not
     *
     * @param values
     * @return
     */
    public static boolean containsNull(Object... values) {
        if (values != null) {
            for (Object value : values)
                if (value == null)
                    return true;
        }
        return false;
    }
}
