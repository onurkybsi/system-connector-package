package nl.tue.systemconnectorpackage.common;

import java.io.IOException;

import com.google.gson.reflect.TypeToken;

/**
 * Provides various functionalities for file operations
 */
public interface FileUtilityService {
    /**
     * Loads JSON file and converts it to given type
     * 
     * @param <T>                           Types of result object
     * @param typeTokenOfResultObjectToLoad Type token of result object
     * @param jsonFilePath                  JSON file path to be loaded
     * @return Loaded JSON object
     * @throws IOException
     */
    <T> T loadJsonFile(TypeToken<T> typeTokenOfResultObjectToLoad, String jsonFilePath) throws IOException;
}
