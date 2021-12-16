package nl.tue.systemconnectorpackage.clients.maas.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import eu.arrowhead.common.dto.shared.OrchestrationResponseDTO;
import eu.arrowhead.common.dto.shared.OrchestrationResultDTO;
import eu.arrowhead.common.dto.shared.SystemResponseDTO;
import nl.tue.systemconnectorpackage.clients.maas.models.Domain;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.ArrowheadHelper;
import nl.tue.systemconnectorpackage.common.HttpUtilityService;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;

public class RepositoryManagerClientDefaultImpTest {
        @Test
        public void RepositoryManagerClientDefaultImp_Throws_InvalidParameterException_If_ArrowheadHelper_Is_Null() {
                // Arrange
                HttpUtilityService mockHttpUtilityService = Mockito.mock(HttpUtilityService.class);
                // Act and Assert
                Assertions.assertThatThrownBy(() -> new RepositoryManagerClientDefaultImp(null, mockHttpUtilityService))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("arrowheadHelper and httpUtilityService cannot be null!");
        }

        @Test
        public void RepositoryManagerClientDefaultImp_Throws_InvalidParameterException_If_HttpUtilityService_Is_Null() {
                // Arrange
                ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
                // Act and Assert
                Assertions.assertThatThrownBy(() -> new RepositoryManagerClientDefaultImp(mockArrowheadHelper, null))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("arrowheadHelper and httpUtilityService cannot be null!");
        }

        @Test
        public void getDomains_Proceed_Orchestration_Via_ArrowheadHelper() {
                // Arrange
                ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
                ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
                OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
                mockOrchestrationResult.setMetadata(new HashMap<>());
                Mockito.doReturn(createValidOrchestrationResponseDTO(mockOrchestrationResult)).when(mockArrowheadHelper)
                                .proceedOrchestration(argumentCaptor.capture());
                HttpUtilityService mockHttpUtilityService = Mockito.mock(HttpUtilityService.class);
                RepositoryManagerClientDefaultImp client = new RepositoryManagerClientDefaultImp(mockArrowheadHelper,
                                mockHttpUtilityService);
                // Act
                client.getDomains("name", 1, 1);
                // Assert
                String actualInput = argumentCaptor.getValue();
                Assertions.assertThat(actualInput).isEqualTo("get-domains");
        }

        private OrchestrationResponseDTO createValidOrchestrationResponseDTO() {
                OrchestrationResultDTO orchestrationResult = new OrchestrationResultDTO();
                orchestrationResult.setMetadata(getGetDomainsServiceDefaultMetadata());
                return createValidOrchestrationResponseDTO(orchestrationResult);
        }

        private HashMap<String, String> getGetDomainsServiceDefaultMetadata() {
                HashMap<String, String> getDomainsServiceDefaultMetadata = new HashMap<>();
                getDomainsServiceDefaultMetadata.put("request-param-name", "name");
                getDomainsServiceDefaultMetadata.put("request-param-offset", "offset");
                getDomainsServiceDefaultMetadata.put("request-param-limit", "limit");
                return getDomainsServiceDefaultMetadata;
        }

        private OrchestrationResponseDTO createValidOrchestrationResponseDTO(OrchestrationResultDTO resultWillBeAdd) {
                OrchestrationResponseDTO orchestrationResponse = new OrchestrationResponseDTO();
                ArrayList<OrchestrationResultDTO> orchestrationResults = new ArrayList<>();
                orchestrationResults.add(resultWillBeAdd);
                orchestrationResponse.setResponse(orchestrationResults);
                return orchestrationResponse;
        }

        @Test
        public void getDomains_Validate_Orchestration_Result_Via_ArrowheadHelper() {
                // Arrange
                ArgumentCaptor<OrchestrationResponseDTO> argumentCaptor = ArgumentCaptor
                                .forClass(OrchestrationResponseDTO.class);
                ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);

                OrchestrationResponseDTO expectedOrchestrationResponse = createValidOrchestrationResponseDTO();
                Mockito.when(mockArrowheadHelper.proceedOrchestration("get-domains"))
                                .thenReturn(expectedOrchestrationResponse);
                Mockito.doNothing().when(mockArrowheadHelper)
                                .validateOrchestrationResponse(argumentCaptor.capture());
                HttpUtilityService mockHttpUtilityService = Mockito.mock(HttpUtilityService.class);
                RepositoryManagerClientDefaultImp client = new RepositoryManagerClientDefaultImp(mockArrowheadHelper,
                                mockHttpUtilityService);
                // Act
                client.getDomains("name", 1, 1);
                // Assert
                OrchestrationResponseDTO actualInput = argumentCaptor.getValue();
                Assertions.assertThat(actualInput).isEqualTo(expectedOrchestrationResponse);
        }

        // @Test
        // public void
        // getDomains_Consumes_Http_Service_With_By_OrchestrationResult_Via_ArrowheadHelper()
        // {
        // // Arrange
        // ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        // OrchestrationResponseDTO expectedOrchestrationResponse =
        // createValidOrchestrationResponseDTO();
        // Mockito.when(mockArrowheadHelper.proceedOrchestration("get-domains"))
        // .thenReturn(expectedOrchestrationResponse);
        // HttpUtilityService mockHttpUtilityService =
        // Mockito.mock(HttpUtilityService.class);
        // RepositoryManagerClientDefaultImp client = new
        // RepositoryManagerClientDefaultImp(mockArrowheadHelper,
        // mockHttpUtilityService);
        // // Act
        // client.getDomains("name", 1, 1);
        // // Assert
        // ArgumentCaptor<OrchestrationResultDTO> argumentCaptor = ArgumentCaptor
        // .forClass(OrchestrationResultDTO.class);
        // Mockito.verify(mockArrowheadHelper, Mockito.times(1))
        // .consumeServiceHTTPByOrchestrationResult(
        // argumentCaptor.capture(),
        // ArgumentMatchers.eq(HashMap.class),
        // ArgumentMatchers.eq(null),
        // ArgumentMatchers.eq(new String[] { "name", "name", "offset", "1",
        // "limit", "1" }));
        // OrchestrationResultDTO actualInput = argumentCaptor.getValue();
        // Assertions.assertThat(actualInput).isEqualTo(expectedOrchestrationResponse.getResponse().get(0));
        // }

        @Test
        public void getDomains_Consumes_Http_Service_With_Name_Offset_Limit_Parameters_By_OrchestrationResult_Via_ArrowheadHelper() {
                // Arrange
                ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
                OrchestrationResponseDTO expectedOrchestrationResponse = createValidOrchestrationResponseDTO();
                Mockito.when(mockArrowheadHelper.proceedOrchestration("get-domains"))
                                .thenReturn(expectedOrchestrationResponse);
                HttpUtilityService mockHttpUtilityService = Mockito.mock(HttpUtilityService.class);
                RepositoryManagerClientDefaultImp client = new RepositoryManagerClientDefaultImp(mockArrowheadHelper,
                                mockHttpUtilityService);
                // Act
                client.getDomains("name", 1, 1);
                // Assert
                ArgumentCaptor<String> argumentCaptor = ArgumentCaptor
                                .forClass(String.class);
                Mockito.verify(mockArrowheadHelper, Mockito.times(1))
                                .consumeServiceHTTPByOrchestrationResult(
                                                Mockito.eq(expectedOrchestrationResponse.getResponse().get(0)),
                                                Mockito.eq(HashMap.class), Mockito.eq(null),
                                                argumentCaptor.capture());
                List<String> actualInput = argumentCaptor.getAllValues();
                Assertions.assertThat(actualInput).containsAll(getGetDomainsServiceDefaultMetadata().values());
        }

        @Test
        public void createDomain_Proceed_Orchestration_Via_ArrowheadHelper() throws IOException {
                // Arrange
                ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
                ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
                Mockito.when(mockArrowheadHelper.getInterfaceName()).thenReturn("INSECURE");
                OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
                mockOrchestrationResult.setMetadata(new HashMap<>());
                mockOrchestrationResult.setProvider(new SystemResponseDTO());
                Mockito.doReturn(createValidOrchestrationResponseDTO(mockOrchestrationResult)).when(mockArrowheadHelper)
                                .proceedOrchestration(argumentCaptor.capture());
                HttpUtilityService mockHttpUtilityService = Mockito.mock(HttpUtilityService.class);
                RepositoryManagerClientDefaultImp client = new RepositoryManagerClientDefaultImp(mockArrowheadHelper,
                                mockHttpUtilityService);
                // Act
                client.createDomain(new Domain(), new MockMultipartFile(
                                "file",
                                "hello.txt",
                                MediaType.TEXT_PLAIN_VALUE,
                                "Hello, World!".getBytes()));
                // Assert
                String actualInput = argumentCaptor.getValue();
                Assertions.assertThat(actualInput).isEqualTo("create-domain");
        }

        @Test
        public void createDomain_Validate_Orchestration_Result_Via_ArrowheadHelper() throws IOException {
                // Arrange
                ArgumentCaptor<OrchestrationResponseDTO> argumentCaptor = ArgumentCaptor
                                .forClass(OrchestrationResponseDTO.class);
                ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
                Mockito.when(mockArrowheadHelper.getInterfaceName()).thenReturn("INSECURE");

                OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
                mockOrchestrationResult.setMetadata(new HashMap<>());
                mockOrchestrationResult.setProvider(new SystemResponseDTO());
                OrchestrationResponseDTO expectedOrchestrationResponse = createValidOrchestrationResponseDTO(mockOrchestrationResult);
                Mockito.when(mockArrowheadHelper.proceedOrchestration("create-domain"))
                                .thenReturn(expectedOrchestrationResponse);
                Mockito.doNothing().when(mockArrowheadHelper)
                                .validateOrchestrationResponse(argumentCaptor.capture());
                HttpUtilityService mockHttpUtilityService = Mockito.mock(HttpUtilityService.class);
                RepositoryManagerClientDefaultImp client = new RepositoryManagerClientDefaultImp(mockArrowheadHelper,
                                mockHttpUtilityService);
                // Act
                client.createDomain(new Domain(), new MockMultipartFile(
                                "file",
                                "hello.txt",
                                MediaType.TEXT_PLAIN_VALUE,
                                "Hello, World!".getBytes()));
                // Assert
                OrchestrationResponseDTO actualInput = argumentCaptor.getValue();
                Assertions.assertThat(actualInput).isEqualTo(expectedOrchestrationResponse);
        }

}
