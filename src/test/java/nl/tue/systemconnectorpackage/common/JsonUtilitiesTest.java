package nl.tue.systemconnectorpackage.common;

import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;

public class JsonUtilitiesTest {
    @Test
    public void convertToJsonString_Returns_Null_If_Given_Parameter_Is_Null() {
        // Arrange
        Object toConvert = null;
        // Act
        String actualValue = JsonUtilities.convertToJsonString(toConvert);
        // Assert
        Assertions.assertThat(actualValue).isNull();
    }

    @Test
    public void convertToJsonString_Returns_Json_Representation_Of_An_Object() {
        // Arrange
        ArrayList<String> toConvert = new ArrayList<>();
        toConvert.add("Onur");
        // Act
        String actualValue = JsonUtilities.convertToJsonString(toConvert);
        // Assert
        String expectedJsonRepresentation = "[\"Onur\"]";
        Assertions.assertThat(actualValue).isEqualTo(expectedJsonRepresentation);
    }

    @Test
    public void convertFromJsonString_Returns_Null_If_Given_JsonString_Parameter_Is_Null() {
        // Arrange
        String jsonString = null;
        // Act
        String actualValue = JsonUtilities.convertFromJsonString(new TypeToken<String>() {
        }, jsonString);
        // Assert
        Assertions.assertThat(actualValue).isNull();
    }

    @Test
    public void convertFromJsonString_Throws_InvalidParameterException_If_Given_TypeToken_Parameter_Is_Null() {
        // Arrange and Act and Assert
        Assertions.assertThatThrownBy((() -> JsonUtilities.convertFromJsonString(null, "[\"Onur\"]")))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    public void convertFromJsonString_Converts_Json_Representation_String_To_A_Java_Object() {
        // Arrange
        String jsonString = "[\"Onur\"]";
        // Act
        ArrayList<String> actualResult = JsonUtilities.convertFromJsonString(new TypeToken<ArrayList<String>>() {
        }, jsonString);
        // Assert
        Assertions.assertThat(actualResult).contains("Onur");
    }
}
