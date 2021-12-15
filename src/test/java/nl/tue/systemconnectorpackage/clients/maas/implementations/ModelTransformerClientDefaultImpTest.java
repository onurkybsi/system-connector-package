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

public class ModelTransformerClientDefaultImpTest {
    @Test
    public void ModelTransformerClientDefaultImp_Throws_InvalidParameterException_If_Given_ArrowheadHelper_Parameter_Is_Null() {
        // Arrange, Act and Assert
        Assertions.assertThatThrownBy(() -> new ModelTransformerClientDefaultImp(null))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("arrowheadHelper cannot be null!");
    }

    @Test
    public void transformModel_Proceed_Orchestration_Via_ArrowheadHelper() {
        // Arrange
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
        mockOrchestrationResult.setMetadata(new HashMap<>());
        Mockito.doReturn(createValidOrchestrationResponseDTO(mockOrchestrationResult)).when(mockArrowheadHelper)
                .proceedOrchestration(argumentCaptor.capture());
        ModelTransformerClientDefaultImp client = new ModelTransformerClientDefaultImp(mockArrowheadHelper);
        // Act
        client.transformModel("model");
        // Assert
        String actualInput = argumentCaptor.getValue();
        Assertions.assertThat(actualInput).isEqualTo("model-transformer");
    }

    private OrchestrationResponseDTO createValidOrchestrationResponseDTO() {
        OrchestrationResultDTO orchestrationResult = new OrchestrationResultDTO();
        orchestrationResult.setMetadata(getGetDomainsServiceDefaultMetadata());
        return createValidOrchestrationResponseDTO(orchestrationResult);
    }

    private HashMap<String, String> getGetDomainsServiceDefaultMetadata() {
        HashMap<String, String> getDomainsServiceDefaultMetadata = new HashMap<>();
        getDomainsServiceDefaultMetadata.put("request-param-format", "format");
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
    public void transformModel_Validate_Orchestration_Result_Via_ArrowheadHelper() {
        // Arrange
        ArgumentCaptor<OrchestrationResponseDTO> argumentCaptor = ArgumentCaptor
                .forClass(OrchestrationResponseDTO.class);
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);

        OrchestrationResponseDTO expectedOrchestrationResponse = createValidOrchestrationResponseDTO();
        Mockito.when(mockArrowheadHelper.proceedOrchestration("model-transformer"))
                .thenReturn(expectedOrchestrationResponse);
        Mockito.doNothing().when(mockArrowheadHelper)
                .validateOrchestrationResponse(argumentCaptor.capture());
        ModelTransformerClientDefaultImp client = new ModelTransformerClientDefaultImp(mockArrowheadHelper);
        // Act
        client.transformModel("model");
        // Assert
        OrchestrationResponseDTO actualInput = argumentCaptor.getValue();
        Assertions.assertThat(actualInput).isEqualTo(expectedOrchestrationResponse);
    }
}
