package nl.tue.systemconnectorpackage.clients.maas.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import eu.arrowhead.common.dto.shared.OrchestrationResponseDTO;
import eu.arrowhead.common.dto.shared.OrchestrationResultDTO;
import eu.arrowhead.common.dto.shared.SystemResponseDTO;
import nl.tue.systemconnectorpackage.clients.maas.models.Domain;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.ArrowheadHelper;
import nl.tue.systemconnectorpackage.common.HttpUtilityService;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;
import nl.tue.systemconnectorpackage.common.models.MultipartFormDataDescription;

@ExtendWith(MockitoExtension.class)
public class RepositoryManagerClientDefaultImpTest {
    @Mock
    private ArrowheadHelper mockArrowheadHelper;
    @Mock
    private HttpUtilityService mockHttpUtilityService;

    @Test
    public void RepositoryManagerClientDefaultImp_Throws_InvalidParameterException_If_ArrowheadHelper_Is_Null() {
        // Act and Assert
        Assertions.assertThatThrownBy(() -> new RepositoryManagerClientDefaultImp(null, mockHttpUtilityService))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("arrowheadHelper and httpUtilityService cannot be null!");
    }

    @Test
    public void RepositoryManagerClientDefaultImp_Throws_InvalidParameterException_If_HttpUtilityService_Is_Null() {
        // Act and Assert
        Assertions.assertThatThrownBy(() -> new RepositoryManagerClientDefaultImp(mockArrowheadHelper, null))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("arrowheadHelper and httpUtilityService cannot be null!");
    }

    @Test
    public void getDomains_Proceeds_ArrowheadOrchestration_Via_ArrowheadHelper() {
        // Arrange
        Mockito.when(mockArrowheadHelper.proceedOrchestration(ArgumentMatchers.any()))
                .thenReturn(createValidOrchestrationResponseDTO());

        RepositoryManagerClientDefaultImp client = new RepositoryManagerClientDefaultImp(mockArrowheadHelper,
                mockHttpUtilityService);

        // Act
        client.getDomains("name", 1, 1);

        // Assert
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(mockArrowheadHelper)
                .proceedOrchestration(argumentCaptor.capture());
        String actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isEqualTo("get-domains");
    }

    private OrchestrationResponseDTO createValidOrchestrationResponseDTO() {
        OrchestrationResultDTO orchestrationResult = new OrchestrationResultDTO();
        orchestrationResult.setMetadata(createValidMockMetadata());
        return createValidOrchestrationResponseDTO(orchestrationResult);
    }

    private HashMap<String, String> createValidMockMetadata() {
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
    public void getDomains_Validates_OrchestrationResult_Via_ArrowheadHelper() {
        // Arrange
        OrchestrationResponseDTO mockOrchestrationResponse = createValidOrchestrationResponseDTO();
        Mockito.when(mockArrowheadHelper.proceedOrchestration(ArgumentMatchers.any()))
                .thenReturn(mockOrchestrationResponse);

        RepositoryManagerClientDefaultImp client = new RepositoryManagerClientDefaultImp(mockArrowheadHelper,
                mockHttpUtilityService);

        // Act
        client.getDomains("name", 1, 1);

        // Assert
        ArgumentCaptor<OrchestrationResponseDTO> argumentCaptor = ArgumentCaptor
                .forClass(OrchestrationResponseDTO.class);
        Mockito.verify(mockArrowheadHelper)
                .validateOrchestrationResponse(argumentCaptor.capture());
        OrchestrationResponseDTO actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isEqualTo(mockOrchestrationResponse);
    }

    @Test
    public void getDomains_Consumes_HttpService_With_Received_OrchestrationResult_Via_ArrowheadHelper() {
        // Arrange
        OrchestrationResponseDTO mockOrchestrationResponse = createValidOrchestrationResponseDTO();
        Mockito.when(mockArrowheadHelper.proceedOrchestration(ArgumentMatchers.any()))
                .thenReturn(mockOrchestrationResponse);

        RepositoryManagerClientDefaultImp client = new RepositoryManagerClientDefaultImp(mockArrowheadHelper,
                mockHttpUtilityService);
        // Act
        client.getDomains("name", 1, 1);

        // Assert
        ArgumentCaptor<OrchestrationResultDTO> argumentCaptor = ArgumentCaptor
                .forClass(OrchestrationResultDTO.class);
        Mockito.verify(mockArrowheadHelper, Mockito.times(1))
                .consumeServiceHTTPByOrchestrationResult(
                        argumentCaptor.capture(),
                        Mockito.eq(HashMap.class),
                        Mockito.eq(null),
                        Mockito.eq("name"), Mockito.eq("name"), Mockito.eq("offset"),
                        Mockito.eq("1"),
                        Mockito.eq("limit"), Mockito.eq("1"));
        OrchestrationResultDTO actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isEqualTo(mockOrchestrationResponse.getResponse().get(0));
    }

    @Test
    public void getDomains_Consumes_HttpService_With_HashMap_ResponseType_Via_ArrowheadHelper() {
        // Arrange
        OrchestrationResponseDTO mockOrchestrationResponse = createValidOrchestrationResponseDTO();
        Mockito.when(mockArrowheadHelper.proceedOrchestration(ArgumentMatchers.any()))
                .thenReturn(mockOrchestrationResponse);

        RepositoryManagerClientDefaultImp client = new RepositoryManagerClientDefaultImp(mockArrowheadHelper,
                mockHttpUtilityService);

        // Act
        client.getDomains("name", 1, 1);

        // Assert
        ArgumentCaptor<Class<?>> argumentCaptor = ArgumentCaptor
                .forClass(Class.class);
        Mockito.verify(mockArrowheadHelper, Mockito.times(1))
                .consumeServiceHTTPByOrchestrationResult(
                        Mockito.eq(mockOrchestrationResponse.getResponse().get(0)),
                        argumentCaptor.capture(), Mockito.eq(null),
                        Mockito.eq("name"), Mockito.eq("name"), Mockito.eq("offset"),
                        Mockito.eq("1"),
                        Mockito.eq("limit"), Mockito.eq("1"));
        Class<?> actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isEqualTo(HashMap.class);
    }

    @Test
    public void getDomains_Consumes_HttpService_With_NoPayload_Via_ArrowheadHelper() {
        // Arrange
        OrchestrationResponseDTO mockOrchestrationResponse = createValidOrchestrationResponseDTO();
        Mockito.when(mockArrowheadHelper.proceedOrchestration(ArgumentMatchers.any()))
                .thenReturn(mockOrchestrationResponse);

        RepositoryManagerClientDefaultImp client = new RepositoryManagerClientDefaultImp(mockArrowheadHelper,
                mockHttpUtilityService);

        // Act
        client.getDomains("name", 1, 1);

        // Assert
        ArgumentCaptor<Object> argumentCaptor = ArgumentCaptor
                .forClass(Object.class);
        Mockito.verify(mockArrowheadHelper, Mockito.times(1))
                .consumeServiceHTTPByOrchestrationResult(
                        Mockito.eq(mockOrchestrationResponse.getResponse().get(0)),
                        Mockito.eq(HashMap.class), argumentCaptor.capture(),
                        Mockito.eq("name"), Mockito.eq("name"), Mockito.eq("offset"),
                        Mockito.eq("1"),
                        Mockito.eq("limit"), Mockito.eq("1"));
        Object actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isNull();
    }

    @Test
    public void getDomains_Consumes_HttpService_With_Given_Name_Offset_Limit_RequestParameters_Via_ArrowheadHelper() {
        // Arrange
        OrchestrationResponseDTO mockOrchestrationResponse = createValidOrchestrationResponseDTO();
        Mockito.when(mockArrowheadHelper.proceedOrchestration(ArgumentMatchers.any()))
                .thenReturn(mockOrchestrationResponse);

        RepositoryManagerClientDefaultImp client = new RepositoryManagerClientDefaultImp(mockArrowheadHelper,
                mockHttpUtilityService);

        // Act
        client.getDomains("name", 1, 1);

        // Assert
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor
                .forClass(String.class);
        Mockito.verify(mockArrowheadHelper, Mockito.times(1))
                .consumeServiceHTTPByOrchestrationResult(
                        Mockito.eq(mockOrchestrationResponse.getResponse().get(0)),
                        Mockito.eq(HashMap.class), Mockito.eq(null),
                        argumentCaptor.capture());
        List<String> actualInput = argumentCaptor.getAllValues();

        Assertions.assertThat(actualInput).containsAll(createValidMockMetadata().values());
    }

    @Test
    public void createDomain_Proceeds_ArrowheadOrchestration_Via_ArrowheadHelper() throws IOException {
        // Arrange
        RepositoryManagerClientDefaultImp client = setupSuccessfulCreateDomainExecution().getKey();
        // Act
        callCreateDomainWithMockParameters(client);
        // Assert
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(mockArrowheadHelper)
                .proceedOrchestration(argumentCaptor.capture());
        String actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isEqualTo("create-domain");
    }

    private SimpleEntry<RepositoryManagerClientDefaultImp, OrchestrationResponseDTO> setupSuccessfulCreateDomainExecution()
            throws IOException {
        OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
        mockOrchestrationResult.setServiceUri("/mockServiceUri");
        mockOrchestrationResult.setMetadata(new HashMap<>());
        SystemResponseDTO mockProvider = getMockProvider();
        mockOrchestrationResult.setProvider(mockProvider);
        OrchestrationResponseDTO mockOrchestrationResponse = createValidOrchestrationResponseDTO(
                mockOrchestrationResult);
        Mockito.when(mockArrowheadHelper.proceedOrchestration(ArgumentMatchers.any()))
                .thenReturn(mockOrchestrationResponse);

        Mockito.doNothing().when(mockArrowheadHelper).validateOrchestrationResponse(ArgumentMatchers.any());
        Mockito.doReturn("http://").when(mockArrowheadHelper).getHttpPrefixByInterfaceName();

        Mockito.when(mockHttpUtilityService.sendHttpPostRequestWithMultipartFormData(Mockito.anyString(),
                Mockito.anyMap(), Mockito.anyList(), Mockito.any())).thenReturn(Mockito.any());

        return new SimpleEntry<>(new RepositoryManagerClientDefaultImp(mockArrowheadHelper,
                mockHttpUtilityService), mockOrchestrationResponse);
    }

    private void callCreateDomainWithMockParameters(RepositoryManagerClientDefaultImp clientWillUseToCall)
            throws IOException {
        clientWillUseToCall.createDomain(new Domain(), new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()));
    }

    @Test
    public void createDomain_Validate_OrchestrationResult_Via_ArrowheadHelper()
            throws IOException {
        // Arrange
        SimpleEntry<RepositoryManagerClientDefaultImp, OrchestrationResponseDTO> clientAndOrchestrationPair = setupSuccessfulCreateDomainExecution();
        // Act
        callCreateDomainWithMockParameters(clientAndOrchestrationPair.getKey());
        // Assert
        ArgumentCaptor<OrchestrationResponseDTO> argumentCaptor = ArgumentCaptor
                .forClass(OrchestrationResponseDTO.class);
        Mockito.verify(mockArrowheadHelper)
                .validateOrchestrationResponse(argumentCaptor.capture());
        OrchestrationResponseDTO actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isEqualTo(clientAndOrchestrationPair.getValue());
    }

    @Test
    public void createDomain_Sends_MultipartFormData_HttpRequest_With_Uri_Received_From_OrchestrationResult_Via_HttpUtilityService()
            throws IOException {
        // Arrange
        RepositoryManagerClientDefaultImp client = setupSuccessfulCreateDomainExecution().getKey();
        // Act
        callCreateDomainWithMockParameters(client);
        // Assert
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor
                .forClass(String.class);
        Mockito.verify(mockHttpUtilityService)
                .sendHttpPostRequestWithMultipartFormData(argumentCaptor.capture(), Mockito.anyMap(),
                        Mockito.anyList(), Mockito.any());
        String actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isEqualTo("http://localhost:8080/mockServiceUri");
    }

    private SystemResponseDTO getMockProvider() {
        SystemResponseDTO mockProvider = new SystemResponseDTO();
        mockProvider.setAddress("localhost");
        mockProvider.setPort(8080);
        return mockProvider;
    }

    @Test
    public void createDomain_Sends_MultipartFormData_HttpRequest_With_Access_Token_Received_From_OrchestrationResult_Via_HttpUtilityService()
            throws IOException {
        // Arrange
        RepositoryManagerClientDefaultImp client = setupSuccessfulCreateDomainExecution().getKey();
        // Act
        callCreateDomainWithMockParameters(client);
        // Assert
        ArgumentCaptor<HashMap<String, String>> argumentCaptor = ArgumentCaptor
                .forClass(HashMap.class);
        Mockito.verify(mockHttpUtilityService)
                .sendHttpPostRequestWithMultipartFormData(Mockito.anyString(), argumentCaptor.capture(),
                        Mockito.anyList(), Mockito.any());
        HashMap<String, String> actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).containsEntry("token", null);
    }

    @Test
    public void createDomain_Sends_MultipartFormData_HttpRequest_With_Given_MultipartFormDataDescription_List__Via_HttpUtilityService()
            throws IOException {
        // Arrange
        RepositoryManagerClientDefaultImp client = setupSuccessfulCreateDomainExecution().getKey();
        // Act
        callCreateDomainWithMockParameters(client);
        // Assert
        ArgumentCaptor<ArrayList<MultipartFormDataDescription>> argumentCaptor = ArgumentCaptor
                .forClass(ArrayList.class);
        Mockito.verify(mockHttpUtilityService)
                .sendHttpPostRequestWithMultipartFormData(Mockito.anyString(),
                        Mockito.anyMap(),
                        argumentCaptor.capture(), Mockito.any());
        ArrayList<MultipartFormDataDescription> actualInput = argumentCaptor.getValue();
        boolean firstDataExpected = actualInput.get(0).getFileName() == "domain";
        boolean secondDataExpected = actualInput.get(1).getFileName() == "file";

        Assertions.assertThat(firstDataExpected && secondDataExpected).isTrue();
    }

    @Test
    public void createDomain_Sends_MultipartFormData_HttpRequest_With_HashMap_ResponseType_Via_HttpUtilityService()
            throws IOException {
        // Arrange
        RepositoryManagerClientDefaultImp client = setupSuccessfulCreateDomainExecution().getKey();
        // Act
        callCreateDomainWithMockParameters(client);
        // Assert
        ArgumentCaptor<Class<?>> argumentCaptor = ArgumentCaptor
                .forClass(Class.class);
        Mockito.verify(mockHttpUtilityService)
                .sendHttpPostRequestWithMultipartFormData(Mockito.anyString(), Mockito.anyMap(),
                        Mockito.anyList(), argumentCaptor.capture());
        Class<?> actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isEqualTo(HashMap.class);
    }

    @Test
    public void updateDomain_Proceeds_ArrowheadOrchestration_Via_ArrowheadHelper() {
        // Arrange
        OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
        mockOrchestrationResult.setMetadata(new HashMap<>() {
            {
                put("http-method", "PUT");
            }
        });
        mockOrchestrationResult.setProvider(getMockProvider());
        OrchestrationResponseDTO mockOrchestrationResponse = createValidOrchestrationResponseDTO(
                mockOrchestrationResult);
        Mockito.when(mockArrowheadHelper.proceedOrchestration(ArgumentMatchers.any()))
                .thenReturn(mockOrchestrationResponse);

        RepositoryManagerClientDefaultImp client = new RepositoryManagerClientDefaultImp(mockArrowheadHelper,
                mockHttpUtilityService);

        // Act
        client.updateDomain(new Domain(), "1");

        // Assert
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(mockArrowheadHelper)
                .proceedOrchestration(argumentCaptor.capture());
        String actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isEqualTo("update-domain");
    }

    @Test
    public void updateDomain_Validates_OrchestrationResult_Via_ArrowheadHelper() {
        // Arrange
        OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
        mockOrchestrationResult.setMetadata(new HashMap<>() {
            {
                put("http-method", "PUT");
            }
        });
        mockOrchestrationResult.setProvider(getMockProvider());
        OrchestrationResponseDTO mockOrchestrationResponse = createValidOrchestrationResponseDTO(
                mockOrchestrationResult);
        Mockito.when(mockArrowheadHelper.proceedOrchestration(ArgumentMatchers.any()))
                .thenReturn(mockOrchestrationResponse);

        RepositoryManagerClientDefaultImp client = new RepositoryManagerClientDefaultImp(mockArrowheadHelper,
                mockHttpUtilityService);

        // Act
        client.updateDomain(new Domain(), "1");

        // Assert
        ArgumentCaptor<OrchestrationResponseDTO> argumentCaptor = ArgumentCaptor
                .forClass(OrchestrationResponseDTO.class);
        Mockito.verify(mockArrowheadHelper)
                .validateOrchestrationResponse(argumentCaptor.capture());
        OrchestrationResponseDTO actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isEqualTo(mockOrchestrationResponse);
    }

    @Test
    public void updateDomain_Consumes_HttpService_With_Given_Parameters_And_OrchestrationResult_Via_ArrowheadHelper() {
        // Arrange
        OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
        mockOrchestrationResult.setMetadata(new HashMap<>() {
            {
                put("http-method", "PUT");
            }
        });
        mockOrchestrationResult.setServiceUri("/mockServiceUri");
        mockOrchestrationResult.setProvider(getMockProvider());
        OrchestrationResponseDTO mockOrchestrationResponse = createValidOrchestrationResponseDTO(
                mockOrchestrationResult);
        Mockito.when(mockArrowheadHelper.proceedOrchestration(ArgumentMatchers.any()))
                .thenReturn(mockOrchestrationResponse);

        RepositoryManagerClientDefaultImp client = new RepositoryManagerClientDefaultImp(mockArrowheadHelper,
                mockHttpUtilityService);

        // Act
        Domain domainParameter = new Domain();
        client.updateDomain(domainParameter, "1");

        // Assert
        Mockito.verify(mockArrowheadHelper, Mockito.times(1))
                .consumeServiceHTTP(Mockito.eq(Domain.class),
                        Mockito.eq(HttpMethod.valueOf(mockOrchestrationResult.getMetadata()
                                .get("http-method"))),
                        Mockito.eq("localhost"),
                        Mockito.eq(8080), Mockito.eq("/mockServiceUri/1"),
                        Mockito.eq(null),
                        Mockito.eq(null),

                        Mockito.eq(domainParameter), Mockito.eq((String[]) null));
    }

    @Test
    public void deleteDomain_Proceeds_ArrowheadOrchestration_Via_ArrowheadHelper() {
        // Arrange
        RepositoryManagerClientDefaultImp client = setupToDeleteDomain().getKey();

        // Act
        client.deleteDomain("1");

        // Assert
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(mockArrowheadHelper)
                .proceedOrchestration(argumentCaptor.capture());
        String actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isEqualTo("delete-domain");
    }

    private SimpleEntry<RepositoryManagerClientDefaultImp, OrchestrationResponseDTO> setupToDeleteDomain() {
        OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
        mockOrchestrationResult.setMetadata(new HashMap<>() {
            {
                put("http-method", "DELETE");
            }
        });
        mockOrchestrationResult.setProvider(getMockProvider());
        mockOrchestrationResult.setServiceUri("/deleteDomain");
        OrchestrationResponseDTO mockOrchestrationResponse = createValidOrchestrationResponseDTO(
                mockOrchestrationResult);
        Mockito.when(mockArrowheadHelper.proceedOrchestration(ArgumentMatchers.any()))
                .thenReturn(mockOrchestrationResponse);

        return new SimpleEntry(new RepositoryManagerClientDefaultImp(mockArrowheadHelper,
                mockHttpUtilityService), mockOrchestrationResponse);
    }

    @Test
    public void deleteDomain_Validates_OrchestrationResult_Via_ArrowheadHelper() {
        // Arrange
        SimpleEntry<RepositoryManagerClientDefaultImp, OrchestrationResponseDTO> mockClientAndOrchestrationResponsePair = setupToDeleteDomain();

        // Act
        mockClientAndOrchestrationResponsePair.getKey().deleteDomain("1");

        // Assert
        ArgumentCaptor<OrchestrationResponseDTO> argumentCaptor = ArgumentCaptor
                .forClass(OrchestrationResponseDTO.class);
        Mockito.verify(mockArrowheadHelper)
                .validateOrchestrationResponse(argumentCaptor.capture());
        OrchestrationResponseDTO actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isEqualTo(mockClientAndOrchestrationResponsePair.getValue());
    }

    @Test
    public void deleteDomain_Consumes_HttpService_With_Given_Parameters_And_OrchestrationResult_Via_ArrowheadHelper() {
        // Arrange
        SimpleEntry<RepositoryManagerClientDefaultImp, OrchestrationResponseDTO> mockClientAndOrchestrationResponsePair = setupToDeleteDomain();

        // Act
        mockClientAndOrchestrationResponsePair.getKey().deleteDomain("1");

        // Assert
        Mockito.verify(mockArrowheadHelper, Mockito.times(1))
                .consumeServiceHTTP(Mockito.eq(Void.class),
                        Mockito.eq(HttpMethod.valueOf(mockClientAndOrchestrationResponsePair
                                .getValue().getResponse().get(0).getMetadata()
                                .get("http-method"))),
                        Mockito.eq("localhost"),
                        Mockito.eq(8080), Mockito.eq("/deleteDomain/1"),
                        Mockito.eq(null),
                        Mockito.eq(null),
                        Mockito.eq(null), Mockito.eq((String[]) null));
    }

}
