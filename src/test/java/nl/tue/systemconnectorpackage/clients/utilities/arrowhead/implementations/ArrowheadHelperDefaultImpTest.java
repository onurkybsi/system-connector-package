package nl.tue.systemconnectorpackage.clients.utilities.arrowhead.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.test.util.ReflectionTestUtils;

import eu.arrowhead.client.library.ArrowheadService;
import eu.arrowhead.common.core.CoreSystem;
import eu.arrowhead.common.dto.shared.OrchestrationResponseDTO;
import eu.arrowhead.common.dto.shared.OrchestrationResultDTO;
import eu.arrowhead.common.dto.shared.ServiceDefinitionResponseDTO;
import eu.arrowhead.common.dto.shared.ServiceRegistryRequestDTO;
import eu.arrowhead.common.dto.shared.ServiceRegistryResponseDTO;
import eu.arrowhead.common.dto.shared.SystemResponseDTO;
import eu.arrowhead.common.exception.UnavailableServerException;
import eu.arrowhead.common.http.HttpService;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.exceptions.ArrowheadOrchestrationException;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.models.ArrowheadServiceInformation;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.models.ArrowheadSystemInformation;
import nl.tue.systemconnectorpackage.common.FileUtilityService;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;
import nl.tue.systemconnectorpackage.common.exceptions.UnexpectedException;
import nl.tue.systemconnectorpackage.common.implementations.FileUtilityServiceDefaultImp;

public class ArrowheadHelperDefaultImpTest {
        private final String MOCK_SYSTEM_DEFINITIONS_PATH = "system-definitions/mock-system-definitions.json";
        private final String EMPTY_SYSTEM_DEFINITION_PATH = "system-definitions/empty-system-definitions.json";

        @Test
        public void registerSystemToArrowhead_Throws_InvalidParameterException_If_Given_SystemDefinitionListResourcePath_Is_Null() {
                // Arrange
                String invalidSystemDefinitionListResourcePath = null;
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                // Act and Assert
                Assertions.assertThatThrownBy(
                                () -> helperImpBase.registerSystemsToArrowhead(invalidSystemDefinitionListResourcePath))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("systemDefinitionListResourcePath is not valid!");
        }

        private ArrowheadHelperDefaultImp buildMockArrowheadHelperImpBase() {
                return buildMockArrowheadHelperImpBase(null);
        }

        private ArrowheadHelperDefaultImp buildMockArrowheadHelperImpBase(
                        FileUtilityService mockFileUtilityService) {
                ArrowheadService mockArrowheadService = Mockito.mock(ArrowheadService.class, Mockito.withSettings());
                FileUtilityService fileUtilityService = mockFileUtilityService == null
                                ? new FileUtilityServiceDefaultImp()
                                : mockFileUtilityService;
                HttpService mockHttpService = Mockito.mock(HttpService.class);

                return Mockito.mock(ArrowheadHelperDefaultImp.class,
                                Mockito.withSettings()
                                                .useConstructor(mockArrowheadService, fileUtilityService,
                                                                mockHttpService)
                                                .defaultAnswer(Mockito.CALLS_REAL_METHODS));
        }

        @Test
        public void registerSystemToArrowhead_Throws_InvalidParameterException_If_Given_SystemDefinitionListResourcePath_Is_Empty() {
                // Arrange
                String invalidSystemDefinitionListResourcePath = "";
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                // Act and Assert
                Assertions.assertThatThrownBy(
                                () -> helperImpBase.registerSystemsToArrowhead(invalidSystemDefinitionListResourcePath))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("systemDefinitionListResourcePath is not valid!");
        }

        @Test
        public void registerSystemToArrowhead_Throws_UnavailableServerException_If_Arrowhead_ServiceRegistry_Server_Is_Unavailable() {
                // Arrange
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                Mockito.when(helperImpBase.arrowheadService.echoCoreSystem(CoreSystem.SERVICE_REGISTRY))
                                .thenReturn(false);
                // Act and Assert
                Assertions.assertThatThrownBy(
                                () -> helperImpBase.registerSystemsToArrowhead(MOCK_SYSTEM_DEFINITIONS_PATH))
                                .isInstanceOf(UnavailableServerException.class)
                                .hasMessageContaining("CoreSystem.SERVICE_REGISTRY is not available!");
        }

        @Test
        public void registerSystemToArrowhead_Throws_UnavailableServerException_If_Arrowhead_Authorization_Server_Is_Unavailable() {
                // Arrange
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                Mockito.when(helperImpBase.arrowheadService.echoCoreSystem(CoreSystem.SERVICE_REGISTRY))
                                .thenReturn(true);
                Mockito.when(helperImpBase.arrowheadService.echoCoreSystem(CoreSystem.AUTHORIZATION))
                                .thenReturn(false);
                // Act and Assert
                Assertions.assertThatThrownBy(
                                () -> helperImpBase.registerSystemsToArrowhead(MOCK_SYSTEM_DEFINITIONS_PATH))
                                .isInstanceOf(UnavailableServerException.class)
                                .hasMessageContaining("CoreSystem.AUTHORIZATION is not available!");
        }

        @Test
        public void registerSystemToArrowhead_Initializes_ArrowheadContext_For_CoreSystem_Authorization_If_The_System_Is_Available()
                        throws UnavailableServerException, JsonSyntaxException, IOException {
                // Arrange
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                setupCoreSystemsCheck(helperImpBase);
                Mockito.doNothing().when(helperImpBase.arrowheadService)
                                .updateCoreServiceURIs(Mockito.any(CoreSystem.class));
                // Act
                helperImpBase.registerSystemsToArrowhead(EMPTY_SYSTEM_DEFINITION_PATH);
                // Assert
                ArgumentCaptor<CoreSystem> argumentCaptor = ArgumentCaptor.forClass(CoreSystem.class);
                Mockito.verify(helperImpBase.arrowheadService, Mockito.times(2))
                                .updateCoreServiceURIs(argumentCaptor.capture());
                CoreSystem actualValue = argumentCaptor.getAllValues().get(0);
                Assertions.assertThat(actualValue).isEqualTo(CoreSystem.AUTHORIZATION);
        }

        private void setupCoreSystemsCheck(ArrowheadHelperDefaultImp helperImpBase) {
                Mockito.when(helperImpBase.arrowheadService.echoCoreSystem(CoreSystem.SERVICE_REGISTRY))
                                .thenReturn(true);
                Mockito.when(helperImpBase.arrowheadService.echoCoreSystem(CoreSystem.AUTHORIZATION))
                                .thenReturn(true);
                Mockito.when(helperImpBase.arrowheadService.echoCoreSystem(CoreSystem.ORCHESTRATOR))
                                .thenReturn(true);
        }

        @Test
        public void registerSystemToArrowhead_Throws_UnavailableServerException_If_Arrowhead_Orchestrator_Server_Is_Unavailable() {
                // Arrange
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                Mockito.when(helperImpBase.arrowheadService.echoCoreSystem(CoreSystem.SERVICE_REGISTRY))
                                .thenReturn(true);
                Mockito.when(helperImpBase.arrowheadService.echoCoreSystem(CoreSystem.AUTHORIZATION))
                                .thenReturn(true);
                Mockito.when(helperImpBase.arrowheadService.echoCoreSystem(CoreSystem.ORCHESTRATOR))
                                .thenReturn(false);
                // Act and Assert
                Assertions.assertThatThrownBy(
                                () -> helperImpBase.registerSystemsToArrowhead(MOCK_SYSTEM_DEFINITIONS_PATH))
                                .isInstanceOf(UnavailableServerException.class)
                                .hasMessageContaining("CoreSystem.ORCHESTRATOR is not available!");
        }

        @Test
        public void registerSystemToArrowhead_Initializes_ArrowheadContext_For_CoreSystem_Orchestrator_If_The_System_Is_Available()
                        throws UnavailableServerException, JsonSyntaxException, IOException {
                // Arrange
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                setupCoreSystemsCheck(helperImpBase);
                Mockito.doNothing().when(helperImpBase.arrowheadService)
                                .updateCoreServiceURIs(Mockito.any(CoreSystem.class));
                // Act
                helperImpBase.registerSystemsToArrowhead(EMPTY_SYSTEM_DEFINITION_PATH);
                // Assert
                ArgumentCaptor<CoreSystem> argumentCaptor = ArgumentCaptor.forClass(CoreSystem.class);
                Mockito.verify(helperImpBase.arrowheadService, Mockito.times(2))
                                .updateCoreServiceURIs(argumentCaptor.capture());
                CoreSystem actualValue = argumentCaptor.getAllValues().get(1);
                Assertions.assertThat(actualValue).isEqualTo(CoreSystem.ORCHESTRATOR);
        }

        @Test
        public void registerSystemToArrowhead_Throws_UnexpectedException_If_Loading_System_Definitions_Fails()
                        throws UnavailableServerException, JsonSyntaxException, IOException {
                // Arrange
                FileUtilityService mockFileUtilityService = Mockito.mock(FileUtilityService.class);
                Mockito.when(mockFileUtilityService.loadJsonFile(ArgumentMatchers.any(TypeToken.class),
                                ArgumentMatchers.anyString()))
                                .thenReturn(null);

                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase(mockFileUtilityService);
                setupCoreSystemsCheck(helperImpBase);
                // Act and Assert
                Assertions.assertThatThrownBy(
                                () -> helperImpBase.registerSystemsToArrowhead(EMPTY_SYSTEM_DEFINITION_PATH))
                                .isInstanceOf(UnexpectedException.class);

        }

        @Test
        public void registerSystemToArrowhead_Does_Not_Register_Any_Provider_If_There_Is_No_Any_SystemDefinition_In_Json_File_In_Given_Path()
                        throws UnavailableServerException, JsonSyntaxException, IOException {
                // Arrange
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                setupCoreSystemsCheck(helperImpBase);
                // Act
                helperImpBase.registerSystemsToArrowhead(EMPTY_SYSTEM_DEFINITION_PATH);
                // Assert
                Mockito.verify(helperImpBase.arrowheadService, Mockito.times(0))
                                .forceRegisterServiceToServiceRegistry(
                                                ArgumentMatchers.any(ServiceRegistryRequestDTO.class));
        }

        @Test
        public void registerSystemToArrowhead_Does_Not_Register_Any_Consumer_If_There_Is_No_Any_SystemDefinition_In_Json_File_In_Given_Path()
                        throws UnavailableServerException, JsonSyntaxException, IOException {
                // TO-DO...
        }

        @Test
        public void registerSystemToArrowhead_Registers_Provider_Services_To_Arrowhead() throws IOException {
                // Arrange
                FileUtilityService mockFileUtilityService = Mockito.mock(FileUtilityService.class);

                ArrayList<ArrowheadSystemInformation> mockProviders = new ArrayList<>();
                List<ArrowheadServiceInformation> mockProvider1Services = new ArrayList<>();
                mockProvider1Services.add(new ArrowheadServiceInformation(Long.valueOf(1),
                                Long.valueOf(1),
                                "mock-service-1", "mock-uri-1", new HashMap<String, String>()));
                mockProvider1Services.add(null);
                mockProviders.add(new ArrowheadSystemInformation("mock-provider-1",
                                "localhost", 5000,
                                mockProvider1Services, new ArrayList<>()));
                mockProviders.add(null);

                Mockito.when(mockFileUtilityService.loadJsonFile(ArgumentMatchers.any(TypeToken.class),
                                ArgumentMatchers.anyString()))
                                .thenReturn(mockProviders);

                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase(mockFileUtilityService);

                ServiceRegistryResponseDTO mockResponse = new ServiceRegistryResponseDTO();
                SystemResponseDTO mockSystem = new SystemResponseDTO();
                mockSystem.setId(Long.valueOf(1));
                mockResponse.setProvider(mockSystem);
                ServiceDefinitionResponseDTO mockServiceDefinition = new ServiceDefinitionResponseDTO();
                mockServiceDefinition.setId(Long.valueOf(1));
                mockResponse.setServiceDefinition(mockServiceDefinition);
                Mockito.when(helperImpBase.arrowheadService
                                .forceRegisterServiceToServiceRegistry(Mockito.any(ServiceRegistryRequestDTO.class)))
                                .thenReturn(mockResponse);

                setupCoreSystemsCheck(helperImpBase);
                // Act
                helperImpBase.registerSystemsToArrowhead("some-file.json");
                // Assert
                ArgumentCaptor<ServiceRegistryRequestDTO> argumentCaptor = ArgumentCaptor
                                .forClass(ServiceRegistryRequestDTO.class);
                Mockito.verify(helperImpBase.arrowheadService)
                                .forceRegisterServiceToServiceRegistry(argumentCaptor.capture());
                List<ServiceRegistryRequestDTO> actualValue = argumentCaptor.getAllValues();
                boolean invokingCountIsLikeExpected = actualValue.size() == 1;
                boolean registeredServiceDefinitionLikeExpected = actualValue.get(0)
                                .getServiceDefinition() == "mock-service-1";
                Assertions.assertThat(invokingCountIsLikeExpected &&
                                registeredServiceDefinitionLikeExpected).isTrue();
        }

        @Test
        public void registerSystemToArrowhead_Registers_Consumers_To_Arrowhead()
                        throws IOException {
                // TO-DO...
        }

        @Test
        public void getInterfaceName_Return_HTTP_SECURE_JSON_If_Ssl_Enabled() {
                // Arrange
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                ReflectionTestUtils.setField(helperImpBase, "sslEnabled", true);
                // Act
                String actualResult = helperImpBase.getInterfaceName();
                // Assert
                Assertions.assertThat(actualResult)
                                .isEqualTo("HTTP-SECURE-JSON");
        }

        @Test
        public void getInterfaceName_Return_HTTP_INSECURE_JSON_If_Ssl_Enabled() {
                // Arrange
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                ReflectionTestUtils.setField(helperImpBase, "sslEnabled", false);
                // Act
                String actualResult = helperImpBase.getInterfaceName();
                // Assert
                Assertions.assertThat(actualResult)
                                .isEqualTo("HTTP-INSECURE-JSON");
        }

        @Test
        public void validateOrchestrationResponse_Throws_ArrowheadOrchestrationException_If_Given_OrchestrationResponse_Is_Null() {
                // Arrange
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                // Act and Assert
                Assertions.assertThatThrownBy(() -> helperImpBase.validateOrchestrationResponse(null))
                                .isInstanceOf(ArrowheadOrchestrationException.class)
                                .hasMessageContaining("No orchestration response received");
        }

        @Test
        public void validateOrchestrationResponse_Throws_ArrowheadOrchestrationException_If_Given_OrchestrationResult_List_Is_Null() {
                // Arrange
                OrchestrationResponseDTO mockOrchestrationResponse = new OrchestrationResponseDTO();
                mockOrchestrationResponse.setResponse(null);
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                // Act and Assert
                Assertions.assertThatThrownBy(
                                () -> helperImpBase.validateOrchestrationResponse(mockOrchestrationResponse))
                                .isInstanceOf(ArrowheadOrchestrationException.class)
                                .hasMessageContaining("No provider found during the orchestration");
        }

        @Test
        public void validateOrchestrationResponse_Throws_ArrowheadOrchestrationException_If_Given_OrchestrationResult_List_Is_Empty() {
                // Arrange
                OrchestrationResponseDTO mockOrchestrationResponse = new OrchestrationResponseDTO();
                mockOrchestrationResponse.setResponse(new ArrayList<OrchestrationResultDTO>());
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                // Act and Assert
                Assertions.assertThatThrownBy(
                                () -> helperImpBase.validateOrchestrationResponse(mockOrchestrationResponse))
                                .isInstanceOf(ArrowheadOrchestrationException.class)
                                .hasMessageContaining("No provider found during the orchestration");
        }

        @Test
        public void consumeServiceHTTPByOrchestrationResult_Throws_InvalidParameterException_If_Given_OrchestrationResult_Is_Null() {
                // Arrange
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                // Act and Assert
                Assertions.assertThatThrownBy(() -> helperImpBase.consumeServiceHTTPByOrchestrationResult(null,
                                HashMap.class, null, new String[] {}))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("orchestrationResult cannot be null!");
        }

        @Test
        public void consumeServiceHTTPByOrchestrationResult_Throws_InvalidParameterException_If_Given_OrchestrationResult_Provider_Is_Null() {
                // Arrange
                OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                // Act and Assert
                Assertions.assertThatThrownBy(
                                () -> helperImpBase.consumeServiceHTTPByOrchestrationResult(mockOrchestrationResult,
                                                HashMap.class, null, new String[] {}))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("orchestrationResult is not valid: Provider is null!");
        }

        @Test
        public void consumeServiceHTTPByOrchestrationResult_Throws_InvalidParameterException_If_Given_OrchestrationResult_Metadata_Is_Null() {
                // Arrange
                OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
                mockOrchestrationResult.setProvider(new SystemResponseDTO());
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                // Act and Assert
                Assertions.assertThatThrownBy(
                                () -> helperImpBase.consumeServiceHTTPByOrchestrationResult(mockOrchestrationResult,
                                                HashMap.class, null, new String[] {}))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("orchestrationResult is not valid: Metadata is null!");
        }

        @Test
        public void consumeServiceHTTPByOrchestrationResult_Calls_ArrowheadService_ConsumeServiceHTTP_By_Given_Parameters() {
                // Arrange
                OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
                mockOrchestrationResult.setServiceUri("mockServiceUri");
                var mockProvider = new SystemResponseDTO();
                mockProvider.setAddress("mockaddress");
                mockProvider.setPort(1234);
                mockOrchestrationResult.setProvider(mockProvider);
                mockOrchestrationResult.setMetadata(new HashMap<String, String>() {
                        {
                                put("http-method", "GET");
                        }
                });

                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();

                Mockito.when(helperImpBase.arrowheadService.consumeServiceHTTP(HashMap.class, HttpMethod.GET,
                                "mockaddress", 1234,
                                "mockServiceUri", "HTTP-INSECURE-JSON", null, null,
                                new String[] { "http-method", "GET" }))
                                .thenReturn(new HashMap<>());
                // Act
                helperImpBase.consumeServiceHTTPByOrchestrationResult(mockOrchestrationResult, HashMap.class, null,
                                new String[] { "http-method", "GET" });

                // Assert
                Mockito.verify(helperImpBase.arrowheadService, Mockito.times(1))
                                .consumeServiceHTTP(HashMap.class, HttpMethod.GET,
                                                "mockaddress", 1234,
                                                "mockServiceUri", "HTTP-INSECURE-JSON", null, null,
                                                new String[] { "http-method", "GET" });
        }

        @Test
        public void extractAuthorizationTokenFromOrchestrationResult_Throws_InvalidParameterException_If_Given_OrchestrationResult_Is_Null() {
                // Arrange
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                // Act and Assert
                Assertions.assertThatThrownBy(
                                () -> helperImpBase.extractAuthorizationTokenFromOrchestrationResult(null))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("orchestrationResult cannot be null!");
        }

        @Test
        public void extractAuthorizationTokenFromOrchestrationResult_Returns_Null_OrchestrationResult_Token_Is_Null() {
                // Arrange
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                // Act
                var actualResult = helperImpBase.extractAuthorizationTokenFromOrchestrationResult(
                                new OrchestrationResultDTO());
                // Act and Assert
                Assertions.assertThat(actualResult)
                                .isNull();
        }

        @Test
        public void extractAuthorizationTokenFromOrchestrationResult_Returns_OrchestrationResult_Token_By_InterfaceName_If_It_Is_Not_Null() {
                // Arrange
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                String mockInterfaceName = "HTTP-INSECURE-JSON";
                var mockOrchestrationResult = new OrchestrationResultDTO();
                mockOrchestrationResult.setAuthorizationTokens(new HashMap<>() {
                        {
                                put(mockInterfaceName, "mockToken");
                        }
                });
                // Act
                var actualResult = helperImpBase
                                .extractAuthorizationTokenFromOrchestrationResult(mockOrchestrationResult);
                // Act and Assert
                Assertions.assertThat(actualResult)
                                .isEqualTo("mockToken");
        }

        @Test
        public void consumeServiceHTTP_Calls_ArrowheadService_consumeServiceHTTP() {
                // Arrange
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                Mockito.when(helperImpBase.arrowheadService.consumeServiceHTTP(HashMap.class, HttpMethod.GET, "address",
                                1234,
                                "serviceUri", "interfaceName", "token", "payload", "queryParams"))
                                .thenReturn(new HashMap<>());
                // Act
                helperImpBase.consumeServiceHTTP(HashMap.class, HttpMethod.GET, "address",
                                1234,
                                "serviceUri", "interfaceName", "token", "payload", "queryParams");
                // Assert
                Mockito.verify(helperImpBase.arrowheadService)
                                .consumeServiceHTTP(HashMap.class, HttpMethod.GET, "address",
                                                1234,
                                                "serviceUri", "interfaceName", "token", "payload", "queryParams");
        }

        @Test
        public void getUriScheme_Returns_Http_If_Ssl_Disabled() {
                // Arrange
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                // Act
                String actualResult = helperImpBase.getUriScheme();
                // Assert
                Assertions.assertThat(actualResult).isEqualTo("http");
        }

        @Test
        public void getUriScheme_Returns_Https_If_Ssl_Enabled() {
                // Arrange
                ArrowheadHelperDefaultImp helperImpBase = buildMockArrowheadHelperImpBase();
                ReflectionTestUtils.setField(helperImpBase, "sslEnabled", true);
                // Act
                String actualResult = helperImpBase.getUriScheme();
                // Assert
                Assertions.assertThat(actualResult).isEqualTo("https");
        }
}