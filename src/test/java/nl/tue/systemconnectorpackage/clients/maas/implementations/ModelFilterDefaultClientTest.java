package nl.tue.systemconnectorpackage.clients.maas.implementations;

import java.util.ArrayList;
import java.util.HashMap;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import eu.arrowhead.common.dto.shared.OrchestrationResponseDTO;
import eu.arrowhead.common.dto.shared.OrchestrationResultDTO;
import nl.tue.systemconnectorpackage.clients.maas.models.TransformationListDTO;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.ArrowheadHelper;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;

@ExtendWith(MockitoExtension.class)
public class ModelFilterDefaultClientTest {
    @Mock
    private ArrowheadHelper mockArrowheadHelper;

    @Test
    public void ModelFilterDefaultClient_Throws_InvalidParameterException_If_Given_ArrowheadHelper_Parameter_Is_Null() {
        // Arrange, Act and Assert
        Assertions.assertThatThrownBy(() -> new ModelFilterDefaultClient(null))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("arrowheadHelper cannot be null");
    }

    @Test
    public void filterModel_Proceed_Orchestration_Via_ArrowheadHelper() {
        // Arrange
        OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
        mockOrchestrationResult.setMetadata(new HashMap<>());
        Mockito.when(mockArrowheadHelper.proceedOrchestration(Mockito.any()))
                .thenReturn(createValidOrchestrationResponseDTO(mockOrchestrationResult));

        ModelFilterDefaultClient client = new ModelFilterDefaultClient(mockArrowheadHelper);

        // Act
        client.filterModel("name", "format", "elems", "repo");

        // Assert
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(mockArrowheadHelper)
                .proceedOrchestration(argumentCaptor.capture());
        String actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isEqualTo("model-filter");
    }

    private OrchestrationResponseDTO createValidOrchestrationResponseDTO() {
        OrchestrationResultDTO orchestrationResult = new OrchestrationResultDTO();
        orchestrationResult.setMetadata(getGetDomainsServiceDefaultMetadata());
        return createValidOrchestrationResponseDTO(orchestrationResult);
    }

    private HashMap<String, String> getGetDomainsServiceDefaultMetadata() {
        HashMap<String, String> getDomainsServiceDefaultMetadata = new HashMap<>();
        getDomainsServiceDefaultMetadata.put("request-param-format", "format");
        getDomainsServiceDefaultMetadata.put("request-param-elems", "elems");
        getDomainsServiceDefaultMetadata.put("request-param-repo", "repo");
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
    public void filterModel_Validate_Orchestration_Result_Via_ArrowheadHelper() {
        // Arrange
        OrchestrationResponseDTO mockOrchestrationResponse = createValidOrchestrationResponseDTO();
        Mockito.when(mockArrowheadHelper.proceedOrchestration(Mockito.any()))
                .thenReturn(mockOrchestrationResponse);

        ModelFilterDefaultClient client = new ModelFilterDefaultClient(mockArrowheadHelper);

        // Act
        client.filterModel("name", "format", "elems", "repo");

        // Assert
        ArgumentCaptor<OrchestrationResponseDTO> argumentCaptor = ArgumentCaptor
                .forClass(OrchestrationResponseDTO.class);
        Mockito.verify(mockArrowheadHelper)
                .validateOrchestrationResponse(argumentCaptor.capture());
        OrchestrationResponseDTO actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isEqualTo(mockOrchestrationResponse);
    }

    @Test
    public void transformModel_Consumes_HttpService_With_Given_Parameters_And_Orchestration_Result_Via_ArrowheadHelper() {
        // Arrange
        OrchestrationResponseDTO mockOrchestrationResponse = createValidOrchestrationResponseDTO();
        Mockito.when(mockArrowheadHelper.proceedOrchestration(Mockito.any()))
                .thenReturn(mockOrchestrationResponse);

        ModelTransformerClientDefaultImp client = new ModelTransformerClientDefaultImp(mockArrowheadHelper);

        // Act
        client.transformModel("format");

        // Assert
        Mockito.verify(mockArrowheadHelper, Mockito.times(1))
                .consumeServiceHTTPByOrchestrationResult(
                        Mockito.eq(mockOrchestrationResponse.getResponse().get(0)),
                        Mockito.eq(TransformationListDTO.class), Mockito.eq(null),
                        Mockito.eq("format"),
                        Mockito.eq("format"));
    }

}
