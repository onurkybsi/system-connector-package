package nl.tue.systemconnectorpackage.clients.utilities.arrowhead.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import eu.arrowhead.client.library.ArrowheadService;
import eu.arrowhead.common.core.CoreSystem;
import eu.arrowhead.common.dto.shared.ServiceDefinitionResponseDTO;
import eu.arrowhead.common.dto.shared.ServiceRegistryRequestDTO;
import eu.arrowhead.common.dto.shared.ServiceRegistryResponseDTO;
import eu.arrowhead.common.dto.shared.SystemResponseDTO;
import eu.arrowhead.common.exception.UnavailableServerException;
import eu.arrowhead.common.http.HttpService;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.models.ArrowheadServiceInformation;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.models.ArrowheadSystemInformation;
import nl.tue.systemconnectorpackage.common.FileUtilityService;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;
import nl.tue.systemconnectorpackage.common.exceptions.UnexpectedException;
import nl.tue.systemconnectorpackage.common.implementations.FileUtilityServiceDefaultImp;

public class ArrowheadHelperImpBaseTest {
        private final String MOCK_SYSTEM_DEFINITIONS_PATH = "system-definitions/mock-system-definitions.json";
        private final String SERVICELESS_MOCK_SYSTEM_DEFINITIONS_PATH = "system-definitions/mock-system-definitions-serviceless.json";
        private final String EMPTY_SYSTEM_DEFINITION_PATH = "system-definitions/empty-system-definitions.json";

        private final String MOCK_SYSTEM_NAME_IN_SERVICELESS_MOCK_SYSTEM_DEFINITIONS_JSON = "mock-system";

        @Test
        public void registerSystemToArrowhead_Throws_InvalidParameterException_If_Given_SystemDefinitionListResourcePath_Parameter_Is_Null() {
                // Arrange
                String invalidSystemDefinitionListResourcePath = null;
                ArrowheadHelperImpBase helperImpBase = buildMockArrowheadHelperImpBase();
                // Act and Assert
                Assertions.assertThatThrownBy(
                                () -> helperImpBase.registerSystemsToArrowhead(invalidSystemDefinitionListResourcePath))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("systemDefinitionListResourcePath is not valid!");
        }

        private ArrowheadHelperImpBase buildMockArrowheadHelperImpBase() {
                return buildMockArrowheadHelperImpBase(null);
        }

        private ArrowheadHelperImpBase buildMockArrowheadHelperImpBase(
                        FileUtilityService mockFileUtilityService) {
                ArrowheadService mockArrowheadService = Mockito.mock(ArrowheadService.class, Mockito.withSettings());
                FileUtilityService fileUtilityService = mockFileUtilityService == null
                                ? new FileUtilityServiceDefaultImp()
                                : mockFileUtilityService;
                HttpService mockHttpService = Mockito.mock(HttpService.class);
                Logger mockLogger = Mockito.mock(Logger.class);

                ArrowheadHelperImpBase helperImpBase = Mockito.mock(ArrowheadHelperImpBase.class,
                                Mockito.withSettings().useConstructor(mockArrowheadService, fileUtilityService,
                                                mockHttpService, mockLogger).defaultAnswer(Mockito.CALLS_REAL_METHODS));
                return helperImpBase;
        }

        @Test
        public void registerSystemToArrowhead_Throws_InvalidParameterException_If_Given_SystemDefinitionListResourcePath_Parameter_Is_Empty() {
                // Arrange
                String invalidSystemDefinitionListResourcePath = "";
                ArrowheadHelperImpBase helperImpBase = buildMockArrowheadHelperImpBase();
                // Act and Assert
                Assertions.assertThatThrownBy(
                                () -> helperImpBase.registerSystemsToArrowhead(invalidSystemDefinitionListResourcePath))
                                .isInstanceOf(InvalidParameterException.class)
                                .hasMessageContaining("systemDefinitionListResourcePath is not valid!");
        }

        @Test
        public void registerSystemToArrowhead_Throws_UnavailableServerException_If_Arrowhead_ServiceRegistry_Server_Is_Unavailable() {
                // Arrange
                ArrowheadHelperImpBase helperImpBase = buildMockArrowheadHelperImpBase();
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
                ArrowheadHelperImpBase helperImpBase = buildMockArrowheadHelperImpBase();
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
                ArrowheadHelperImpBase helperImpBase = buildMockArrowheadHelperImpBase();
                setupCoreSystemsCheck(helperImpBase);
                ArgumentCaptor<CoreSystem> argumentCaptor = ArgumentCaptor.forClass(CoreSystem.class);
                Mockito.doNothing().when(helperImpBase.arrowheadService)
                                .updateCoreServiceURIs(argumentCaptor.capture());
                // Act
                helperImpBase.registerSystemsToArrowhead(EMPTY_SYSTEM_DEFINITION_PATH);
                // Assert
                CoreSystem actualValue = argumentCaptor.getAllValues().get(0);
                Assertions.assertThat(actualValue).isEqualTo(CoreSystem.AUTHORIZATION);
        }

        private void setupCoreSystemsCheck(ArrowheadHelperImpBase helperImpBase) {
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
                ArrowheadHelperImpBase helperImpBase = buildMockArrowheadHelperImpBase();
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
                ArrowheadHelperImpBase helperImpBase = buildMockArrowheadHelperImpBase();
                setupCoreSystemsCheck(helperImpBase);
                ArgumentCaptor<CoreSystem> argumentCaptor = ArgumentCaptor.forClass(CoreSystem.class);
                Mockito.doNothing().when(helperImpBase.arrowheadService)
                                .updateCoreServiceURIs(argumentCaptor.capture());
                // Act
                helperImpBase.registerSystemsToArrowhead(EMPTY_SYSTEM_DEFINITION_PATH);
                // Assert
                CoreSystem actualValue = argumentCaptor.getAllValues().get(1);
                Assertions.assertThat(actualValue).isEqualTo(CoreSystem.ORCHESTRATOR);
        }

        @Test
        public void registerSystemToArrowhead_Loads_Systems_From_Json_File_By_Given_FilePath_To_LocalConnectedSystemRepository()
                        throws UnavailableServerException, JsonSyntaxException, IOException {
                // Arrange
                ArrowheadHelperImpBase helperImpBase = buildMockArrowheadHelperImpBase();
                setupCoreSystemsCheck(helperImpBase);
                // Act
                helperImpBase.registerSystemsToArrowhead(SERVICELESS_MOCK_SYSTEM_DEFINITIONS_PATH);
                // Assert
                Assertions
                                .assertThat(helperImpBase
                                                .checkSystemIsExistByName(
                                                                MOCK_SYSTEM_NAME_IN_SERVICELESS_MOCK_SYSTEM_DEFINITIONS_JSON))
                                .isTrue();
        }

        @Test
        public void registerSystemToArrowhead_Throws_UnexpectedException_If_Loading_System_Definitions_Fails()
                        throws UnavailableServerException, JsonSyntaxException, IOException {
                // Arrange
                FileUtilityService mockFileUtilityService = Mockito.mock(FileUtilityService.class);
                Mockito.when(mockFileUtilityService.loadJsonFile(ArgumentMatchers.any(TypeToken.class),
                                ArgumentMatchers.anyString()))
                                .thenReturn(null);

                ArrowheadHelperImpBase helperImpBase = buildMockArrowheadHelperImpBase(mockFileUtilityService);
                setupCoreSystemsCheck(helperImpBase);
                // Act and Assert
                Assertions.assertThatThrownBy(
                                () -> helperImpBase.registerSystemsToArrowhead(EMPTY_SYSTEM_DEFINITION_PATH))
                                .isInstanceOf(UnexpectedException.class);

        }

        @Test
        public void registerSystemToArrowhead_Does_Not_Register_Any_Provider_If_There_Is_No_Any_System_Definition_In_Json_File_In_Given_Path()
                        throws UnavailableServerException, JsonSyntaxException, IOException {
                // Arrange
                ArrowheadHelperImpBase helperImpBase = buildMockArrowheadHelperImpBase();
                setupCoreSystemsCheck(helperImpBase);
                // Act
                helperImpBase.registerSystemsToArrowhead(EMPTY_SYSTEM_DEFINITION_PATH);
                // Assert
                Mockito.verify(helperImpBase.arrowheadService, Mockito.times(0))
                                .forceRegisterServiceToServiceRegistry(
                                                ArgumentMatchers.any(ServiceRegistryRequestDTO.class));
        }

        @Test
        public void registerSystemToArrowhead_Does_Not_Register_Any_Consumer_If_There_Is_No_Any_System_Definition_In_Json_File_In_Given_Path()
                        throws UnavailableServerException, JsonSyntaxException, IOException {
                // Arrange
                ArrowheadHelperImpBase helperImpBase = buildMockArrowheadHelperImpBase();
                setupCoreSystemsCheck(helperImpBase);
                ArgumentCaptor<List<ArrowheadSystemInformation>> argumentCaptor = ArgumentCaptor.forClass(List.class);
                Mockito.doNothing().when(helperImpBase).registerConsumers(argumentCaptor.capture());
                // Act
                helperImpBase.registerSystemsToArrowhead(EMPTY_SYSTEM_DEFINITION_PATH);
                // Assert
                List<ArrowheadSystemInformation> actualValue = argumentCaptor.getValue();
                Assertions.assertThat(actualValue).isEmpty();
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

                ArrowheadHelperImpBase helperImpBase = buildMockArrowheadHelperImpBase(mockFileUtilityService);

                ArgumentCaptor<ServiceRegistryRequestDTO> argumentCaptor = ArgumentCaptor
                                .forClass(ServiceRegistryRequestDTO.class);
                ServiceRegistryResponseDTO mockResponse = new ServiceRegistryResponseDTO();
                SystemResponseDTO mockSystem = new SystemResponseDTO();
                mockSystem.setId(Long.valueOf(1));
                mockResponse.setProvider(mockSystem);
                ServiceDefinitionResponseDTO mockServiceDefinition = new ServiceDefinitionResponseDTO();
                mockServiceDefinition.setId(Long.valueOf(1));
                mockResponse.setServiceDefinition(mockServiceDefinition);
                Mockito.doReturn(mockResponse).when(helperImpBase.arrowheadService)
                                .forceRegisterServiceToServiceRegistry(argumentCaptor.capture());

                setupCoreSystemsCheck(helperImpBase);
                // Act
                helperImpBase.registerSystemsToArrowhead("some-file.json");
                // Assert
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
                // Arrange
                FileUtilityService mockFileUtilityService = Mockito.mock(FileUtilityService.class);
                ArrayList<ArrowheadSystemInformation> mockConsumers = new ArrayList<>();
                List<String> consumedMockServiceNames = new ArrayList<>();
                consumedMockServiceNames.add("mock-service");
                mockConsumers.add(new ArrowheadSystemInformation("mock-consumer-1",
                                "localhost", 5000,
                                new ArrayList<>(), consumedMockServiceNames));
                Mockito.doReturn(mockConsumers).when(mockFileUtilityService).loadJsonFile(
                                ArgumentMatchers.any(TypeToken.class),
                                ArgumentMatchers.anyString());

                ArrowheadHelperImpBase helperImpBase = buildMockArrowheadHelperImpBase(mockFileUtilityService);
                ArgumentCaptor<List<ArrowheadSystemInformation>> argumentCaptor = ArgumentCaptor.forClass(List.class);
                Mockito.doNothing().when(helperImpBase).registerConsumers(argumentCaptor.capture());
                setupCoreSystemsCheck(helperImpBase);
                // Act
                helperImpBase.registerSystemsToArrowhead("some-file.json");
                // Assert
                List<List<ArrowheadSystemInformation>> actualValue = argumentCaptor.getAllValues();
                boolean invokingCountIsLikeExpected = actualValue.size() == 1;
                boolean callingParameterIsLikeExpected = actualValue.get(0).containsAll(mockConsumers);
                Assertions.assertThat(invokingCountIsLikeExpected &&
                                callingParameterIsLikeExpected).isTrue();
        }
}
