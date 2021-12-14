package nl.tue.systemconnectorpackage.common.implementations;

import java.io.IOException;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;

public class FileUtilityServiceDefaultImpTest {
    private final String MOCK_JSON_FILE_PATH = "mock-json-file.json";
    private final String FIRST_VALUE_IN_MOCK_JSON_FILE_LIST = "value";

    @Test
    public void loadJsonFile_Throws_InvalidParameterException_If_Given_TypeToken_Parameter_Is_Null() {
        // Arrange and Act and Assert
        Assertions.assertThatThrownBy(() -> new FileUtilityServiceDefaultImp().loadJsonFile(null,
                "jsonFilePath"))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("typeTokenOfResultObjectToLoad is not valid!");
    }

    @Test
    public void loadJsonFile_Throws_InvalidParameterException_If_Given_Json_File_Path_Parameter_Is_Null() {
        // Arrange and Act and Assert
        Assertions.assertThatThrownBy(() -> new FileUtilityServiceDefaultImp().loadJsonFile(new TypeToken<String>() {
        }, null))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("jsonFilePath is not valid!");
    }

    @Test
    public void loadJsonFile_Throws_InvalidParameterException_If_Given_Json_File_Path_Parameter_Is_Empty() {
        // Arrange and Act and Assert
        Assertions.assertThatThrownBy(() -> new FileUtilityServiceDefaultImp().loadJsonFile(new TypeToken<String>() {
        }, ""))
                .hasMessageContaining("jsonFilePath is not valid!");
    }

    @Test
    public void loadJsonFile_Throws_InvalidParameterException_If_Given_Json_File_Path_Parameter_Does_Not_End_Dot_Json() {
        // Arrange and Act and Assert
        Assertions.assertThatThrownBy(() -> new FileUtilityServiceDefaultImp().loadJsonFile(new TypeToken<String>() {
        }, "some.jso"))
                .hasMessageContaining("jsonFilePath is not valid!");
    }

    @Test
    public void loadJsonFile_Loads_Json_File_And_Convert_It_To_Given_Type_Java_Object() throws IOException {
        // Act
        List<String> mockLoadingResult = new FileUtilityServiceDefaultImp().loadJsonFile(new TypeToken<List<String>>() {
        }, MOCK_JSON_FILE_PATH);
        // Assert
        Assertions.assertThat(mockLoadingResult.get(0))
                .isEqualTo(FIRST_VALUE_IN_MOCK_JSON_FILE_LIST);
    }
}
