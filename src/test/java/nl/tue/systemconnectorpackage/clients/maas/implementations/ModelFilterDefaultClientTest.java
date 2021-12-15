package nl.tue.systemconnectorpackage.clients.maas.implementations;

import java.util.ArrayList;
import java.util.HashMap;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import eu.arrowhead.common.dto.shared.OrchestrationResponseDTO;
import eu.arrowhead.common.dto.shared.OrchestrationResultDTO;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.ArrowheadHelper;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;

public class ModelFilterDefaultClientTest {
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
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
        mockOrchestrationResult.setMetadata(new HashMap<>());
        Mockito.doReturn(createValidOrchestrationResponseDTO(mockOrchestrationResult)).when(mockArrowheadHelper)
                .proceedOrchestration(argumentCaptor.capture());
        ModelFilterDefaultClient client = new ModelFilterDefaultClient(mockArrowheadHelper);
        // Act
        client.filterModel("model", "format", "elems", "repo");
        // Assert
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
        ArgumentCaptor<OrchestrationResponseDTO> argumentCaptor = ArgumentCaptor
                .forClass(OrchestrationResponseDTO.class);
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);

        OrchestrationResponseDTO expectedOrchestrationResponse = createValidOrchestrationResponseDTO();
        Mockito.when(mockArrowheadHelper.proceedOrchestration("model-filter"))
                .thenReturn(expectedOrchestrationResponse);
        Mockito.doNothing().when(mockArrowheadHelper)
                .validateOrchestrationResponse(argumentCaptor.capture());
        ModelFilterDefaultClient client = new ModelFilterDefaultClient(mockArrowheadHelper);
        // Act
        client.filterModel("model", "format", "elems", "repo");
        // Assert
        OrchestrationResponseDTO actualInput = argumentCaptor.getValue();
        Assertions.assertThat(actualInput).isEqualTo(expectedOrchestrationResponse);
    }

}
