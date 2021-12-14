package nl.tue.systemconnectorpackage.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilitiesTest {
    @Test
    public void isValid_Returns_False_If_Given_String_Value_Is_Null() {
        // Arrange
        String stringValue = null;
        // Act
        boolean actualResult = StringUtilities.isValid(stringValue);
        // Assert
        Assertions.assertThat(actualResult).isFalse();
    }

    @Test
    public void isValid_Returns_False_If_Given_String_Is_Empty_String() {
        // Arrange
        String stringValue = " ";
        // Act
        boolean actualResult = StringUtilities.isValid(stringValue);
        // Assert
        Assertions.assertThat(actualResult).isFalse();
    }

    @Test
    public void isValid_Returns_True_If_Given_String_Has_A_Value() {
        // Arrange
        String stringValue = "someValue";
        // Act
        boolean actualResult = StringUtilities.isValid(stringValue);
        // Assert
        Assertions.assertThat(actualResult).isTrue();
    }
}
