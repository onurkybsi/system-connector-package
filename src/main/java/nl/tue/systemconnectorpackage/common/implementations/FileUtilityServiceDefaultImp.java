package nl.tue.systemconnectorpackage.common.implementations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.google.gson.reflect.TypeToken;

import org.springframework.core.io.ClassPathResource;

import nl.tue.systemconnectorpackage.common.FileUtilityService;
import nl.tue.systemconnectorpackage.common.JsonUtilities;
import nl.tue.systemconnectorpackage.common.StringUtilities;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;

/**
 * Default implementation of FileUtilityService
 */
public class FileUtilityServiceDefaultImp implements FileUtilityService {
    @Override
    public <T> T loadJsonFile(TypeToken<T> typeTokenOfResultObjectToLoad, String jsonFilePath)
            throws IOException {
        if (typeTokenOfResultObjectToLoad == null)
            throw new InvalidParameterException("typeTokenOfResultObjectToLoad is not valid!");
        if (!StringUtilities.isValid(jsonFilePath) || !jsonFilePath.endsWith(".json"))
            throw new InvalidParameterException("jsonFilePath is not valid!");

        File resource = new ClassPathResource(jsonFilePath).getFile();
        String jsonString = new String(Files.readAllBytes(resource.toPath()));
        return JsonUtilities
                .convertFromJsonString(typeTokenOfResultObjectToLoad, jsonString);
    }
}
