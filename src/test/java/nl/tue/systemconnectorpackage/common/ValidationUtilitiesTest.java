package nl.tue.systemconnectorpackage.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidationUtilitiesTest {
    @Test
    public void containsNull_Returns_False_If_Given_Parameter_Values_Is_Null() {
        // Arrange
        Object[] values = null;
        // Act
        boolean actualResult = ValidationUtilities.containsNull(values);
        // Assert
        Assertions.assertThat(actualResult).isFalse();
    }

    @Test
    public void containsNull_Returns_True_If_There_Is_A_Null_Value_In_Given_Parameters_Values() {
        // Arrange
        Object[] values = new Object[] { null, new Object() };
        // Act
        boolean actualResult = ValidationUtilities.containsNull(values);
        // Assert
        Assertions.assertThat(actualResult).isTrue();
    }

    @Test
    public void containsNull_Returns_False_If_There_Is_No_Any_Null_Value_In_Given_Parameter_Values() {
        // Arrange
        Object[] values = new Object[] { new Object(), new Object() };
        // Act
        boolean actualResult = ValidationUtilities.containsNull(values);
        // Assert
        Assertions.assertThat(actualResult).isFalse();
    }
}
