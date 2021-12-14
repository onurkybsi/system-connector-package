package nl.tue.systemconnectorpackage.common.implementations;

import java.util.ArrayList;
import java.util.HashMap;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;
import nl.tue.systemconnectorpackage.common.models.MultipartFormDataDescription;

public class HttpUtilityServiceTest {
        @Test
        public void sendHttpPostRequestWithMultipartFormData_Throws_InvalidParameterException_If_Given_Uri_Parameter_Is_Null() {
                // Arrange and Act and Assert
                Assertions
                                .assertThatThrownBy(
                                                () -> new HttpUtilityServiceDefaultImp()
                                                                .sendHttpPostRequestWithMultipartFormData(null,
                                                                                new HashMap<String, String>() {
                                                                                },
                                                                                new ArrayList<MultipartFormDataDescription>() {
                                                                                }, ResponseEntity.class))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("HttpRequest sending parameters are not valid!");
        }

        @Test
        public void sendHttpPostRequestWithMultipartFormData_Throws_InvalidParameterException_If_Given_MultipartFormDataDescriptions_Parameter_Is_Null() {
                // Arrange and Act and Assert
                Assertions
                                .assertThatThrownBy(
                                                () -> new HttpUtilityServiceDefaultImp()
                                                                .sendHttpPostRequestWithMultipartFormData("uri",
                                                                                new HashMap<String, String>() {
                                                                                }, null, ResponseEntity.class))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("HttpRequest sending parameters are not valid!");
        }

        @Test
        public void sendHttpPostRequestWithMultipartFormData_Throws_InvalidParameterException_If_Given_ResponseType_Parameter_Is_Null() {
                // Arrange and Act and Assert
                Assertions
                                .assertThatThrownBy(
                                                () -> new HttpUtilityServiceDefaultImp()
                                                                .sendHttpPostRequestWithMultipartFormData("uri",
                                                                                new HashMap<String, String>() {
                                                                                },
                                                                                new ArrayList<MultipartFormDataDescription>() {
                                                                                }, null))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("HttpRequest sending parameters are not valid!");
        }

        @Test
        public void sendHttpPostRequestWithMultipartFormData_Throws_InvalidParameterException_If_One_Of_MultipartFormDataDescription_Of_Given_MultipartFormDataDescriptions_Parameter_Is_Null() {
                // Arrange
                ArrayList<MultipartFormDataDescription> multipartFormDataDescriptions = new ArrayList<>();
                multipartFormDataDescriptions.add(null);
                // Act and Assert
                Assertions
                                .assertThatThrownBy(
                                                () -> new HttpUtilityServiceDefaultImp()
                                                                .sendHttpPostRequestWithMultipartFormData("uri",
                                                                                new HashMap<String, String>() {
                                                                                }, multipartFormDataDescriptions,
                                                                                ResponseEntity.class))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("multipartFormDataDescription is not valid!");
        }

        @Test
        public void sendHttpPostRequestWithMultipartFormData_Throws_InvalidParameterException_If_One_Of_MultipartFormDataDescription_FileName_Of_Given_MultipartFormDataDescriptions_Parameter_Is_Null() {
                // Arrange
                ArrayList<MultipartFormDataDescription> multipartFormDataDescriptions = new ArrayList<>();
                multipartFormDataDescriptions.add(new MultipartFormDataDescription(null, "fileContent", ".json"));
                // Act and Assert
                Assertions
                                .assertThatThrownBy(
                                                () -> new HttpUtilityServiceDefaultImp()
                                                                .sendHttpPostRequestWithMultipartFormData("uri",
                                                                                new HashMap<String, String>() {
                                                                                }, multipartFormDataDescriptions,
                                                                                ResponseEntity.class))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("fileName is not valid!");
        }

        @Test
        public void sendHttpPostRequestWithMultipartFormData_Throws_InvalidParameterException_If_One_Of_MultipartFormDataDescription_FileName_Of_Given_MultipartFormDataDescriptions_Parameter_Is_Empty() {
                // Arrange
                ArrayList<MultipartFormDataDescription> multipartFormDataDescriptions = new ArrayList<>();
                multipartFormDataDescriptions.add(new MultipartFormDataDescription("", "fileContent", ".json"));
                // Act and Assert
                Assertions
                                .assertThatThrownBy(
                                                () -> new HttpUtilityServiceDefaultImp()
                                                                .sendHttpPostRequestWithMultipartFormData("uri",
                                                                                new HashMap<String, String>() {
                                                                                }, multipartFormDataDescriptions,
                                                                                ResponseEntity.class))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("fileName is not valid!");
        }

        @Test
        public void sendHttpPostRequestWithMultipartFormData_Throws_InvalidParameterException_If_One_Of_MultipartFormDataDescription_FileContent_Of_Given_MultipartFormDataDescriptions_Parameter_Is_Null() {
                // Arrange
                ArrayList<MultipartFormDataDescription> multipartFormDataDescriptions = new ArrayList<>();
                multipartFormDataDescriptions.add(new MultipartFormDataDescription("fileName", null, ".json"));
                // Act and Assert
                Assertions
                                .assertThatThrownBy(
                                                () -> new HttpUtilityServiceDefaultImp()
                                                                .sendHttpPostRequestWithMultipartFormData("uri",
                                                                                new HashMap<String, String>() {
                                                                                }, multipartFormDataDescriptions,
                                                                                ResponseEntity.class))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("fileContent is not valid!");
        }

        @Test
        public void sendHttpPostRequestWithMultipartFormData_Throws_InvalidParameterException_If_One_Of_MultipartFormDataDescription_FileContent_Of_Given_MultipartFormDataDescriptions_Parameter_Is_Empty() {
                // Arrange
                ArrayList<MultipartFormDataDescription> multipartFormDataDescriptions = new ArrayList<>();
                multipartFormDataDescriptions.add(new MultipartFormDataDescription("fileName", "", ".json"));
                // Act and Assert
                Assertions
                                .assertThatThrownBy(
                                                () -> new HttpUtilityServiceDefaultImp()
                                                                .sendHttpPostRequestWithMultipartFormData("uri",
                                                                                new HashMap<String, String>() {
                                                                                }, multipartFormDataDescriptions,
                                                                                ResponseEntity.class))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("fileContent is not valid!");
        }
}
