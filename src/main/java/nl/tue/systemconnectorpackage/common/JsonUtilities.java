package nl.tue.systemconnectorpackage.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;

/**
 * Provides various functionalities for JSON operations
 */
public class JsonUtilities {
    private JsonUtilities() {
        throw new IllegalStateException("This is an utility class!");
    }

    /**
     * Converts a Java object to a string which represents a JSON value
     * 
     * @param toConvert Object to convert
     * @return String which represents a JSON value
     */
    public static String convertToJsonString(Object toConvert) {
        if (toConvert == null)
            return null;
        return (new Gson()).toJson(toConvert);
    }

    /**
     * Converts a string which represents a JSON value to a Java object
     * 
     * @param <T>        Type of return value Java object
     * @param jsonString String which represents a JSON value
     * @return Java object of JSON value representation
     */
    public static <T> T convertFromJsonString(TypeToken<T> typeTokenOfConvertedObject, String jsonString) {
        if (jsonString == null)
            return null;
        if (typeTokenOfConvertedObject == null)
            throw new InvalidParameterException("typeOfConvertedObject cannot be null!");
        return (new Gson()).fromJson(jsonString, typeTokenOfConvertedObject.getType());
    }
}
