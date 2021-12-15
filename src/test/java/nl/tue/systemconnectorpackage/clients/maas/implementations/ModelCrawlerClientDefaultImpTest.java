package nl.tue.systemconnectorpackage.clients.maas.implementations;

import java.util.ArrayList;
import java.util.HashMap;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import eu.arrowhead.common.dto.shared.OrchestrationResponseDTO;
import eu.arrowhead.common.dto.shared.OrchestrationResultDTO;
import nl.tue.systemconnectorpackage.clients.maas.models.CrawlerOptionsDTO;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.ArrowheadHelper;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;

public class ModelCrawlerClientDefaultImpTest {
    @Test
    public void ModelCrawlerClientDefaultImpModelFilterDefaultClient_Throws_InvalidParameterException_If_Given_ArrowheadHelper_Parameter_Is_Null() {
        // Arrange, Act and Assert
        Assertions.assertThatThrownBy(() -> new ModelCrawlerClientDefaultImp(null))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("arrowheadHelper cannot be null!");
    }

    @Test
    public void startCrawlerTask_Proceed_Orchestration_Via_ArrowheadHelper() {
        // Arrange
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
        mockOrchestrationResult.setMetadata(new HashMap<>());
        Mockito.doReturn(createValidOrchestrationResponseDTO(mockOrchestrationResult)).when(mockArrowheadHelper)
                .proceedOrchestration(argumentCaptor.capture());
        ModelCrawlerClientDefaultImp client = new ModelCrawlerClientDefaultImp(mockArrowheadHelper);
        // Act
        client.startCrawlerTask(new CrawlerOptionsDTO());
        // Assert
        String actualInput = argumentCaptor.getValue();
        Assertions.assertThat(actualInput).isEqualTo("model-crawler");
    }

    private OrchestrationResponseDTO createValidOrchestrationResponseDTO() {
        OrchestrationResultDTO orchestrationResult = new OrchestrationResultDTO();
        orchestrationResult.setMetadata(getGetDomainsServiceDefaultMetadata());
        return createValidOrchestrationResponseDTO(orchestrationResult);
    }

    private HashMap<String, String> getGetDomainsServiceDefaultMetadata() {
        return new HashMap<>();
    }

    private OrchestrationResponseDTO createValidOrchestrationResponseDTO(OrchestrationResultDTO resultWillBeAdd) {
        OrchestrationResponseDTO orchestrationResponse = new OrchestrationResponseDTO();
        ArrayList<OrchestrationResultDTO> orchestrationResults = new ArrayList<>();
        orchestrationResults.add(resultWillBeAdd);
        orchestrationResponse.setResponse(orchestrationResults);
        return orchestrationResponse;
    }

    @Test
    public void startCrawlerTask_Validate_Orchestration_Result_Via_ArrowheadHelper() {
        // Arrange
        ArgumentCaptor<OrchestrationResponseDTO> argumentCaptor = ArgumentCaptor
                .forClass(OrchestrationResponseDTO.class);
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);

        OrchestrationResponseDTO expectedOrchestrationResponse = createValidOrchestrationResponseDTO();
        Mockito.when(mockArrowheadHelper.proceedOrchestration("model-crawler"))
                .thenReturn(expectedOrchestrationResponse);
        Mockito.doNothing().when(mockArrowheadHelper)
                .validateOrchestrationResponse(argumentCaptor.capture());
        ModelCrawlerClientDefaultImp client = new ModelCrawlerClientDefaultImp(mockArrowheadHelper);
        // Act
        client.startCrawlerTask(new CrawlerOptionsDTO());
        // Assert
        OrchestrationResponseDTO actualInput = argumentCaptor.getValue();
        Assertions.assertThat(actualInput).isEqualTo(expectedOrchestrationResponse);
    }

    @Test
    public void stopCrawlerTask_Proceed_Orchestration_Via_ArrowheadHelper() {
        // Arrange
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
        mockOrchestrationResult.setMetadata(new HashMap<>());
        Mockito.doReturn(createValidOrchestrationResponseDTO(mockOrchestrationResult)).when(mockArrowheadHelper)
                .proceedOrchestration(argumentCaptor.capture());
        ModelCrawlerClientDefaultImp client = new ModelCrawlerClientDefaultImp(mockArrowheadHelper);
        // Act
        client.stopCrawlerTask();
        // Assert
        String actualInput = argumentCaptor.getValue();
        Assertions.assertThat(actualInput).isEqualTo("model-crawler-stop");
    }

    @Test
    public void stopCrawlerTask_Validate_Orchestration_Result_Via_ArrowheadHelper() {
        // Arrange
        ArgumentCaptor<OrchestrationResponseDTO> argumentCaptor = ArgumentCaptor
                .forClass(OrchestrationResponseDTO.class);
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);

        OrchestrationResponseDTO expectedOrchestrationResponse = createValidOrchestrationResponseDTO();
        Mockito.when(mockArrowheadHelper.proceedOrchestration("model-crawler-stop"))
                .thenReturn(expectedOrchestrationResponse);
        Mockito.doNothing().when(mockArrowheadHelper)
                .validateOrchestrationResponse(argumentCaptor.capture());
        ModelCrawlerClientDefaultImp client = new ModelCrawlerClientDefaultImp(mockArrowheadHelper);
        // Act
        client.stopCrawlerTask();
        // Assert
        OrchestrationResponseDTO actualInput = argumentCaptor.getValue();
        Assertions.assertThat(actualInput).isEqualTo(expectedOrchestrationResponse);
    }
}
