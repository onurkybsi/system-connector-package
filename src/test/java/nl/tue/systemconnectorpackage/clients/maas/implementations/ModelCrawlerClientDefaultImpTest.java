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
import nl.tue.systemconnectorpackage.clients.maas.models.CrawlerOptionsDTO;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.ArrowheadHelper;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;

@ExtendWith(MockitoExtension.class)
public class ModelCrawlerClientDefaultImpTest {
    @Mock
    private ArrowheadHelper mockArrowheadHelper;

    @Test
    public void ModelCrawlerClientDefaultImpModelFilterDefaultClient_Throws_InvalidParameterException_If_Given_ArrowheadHelper_Parameter_Is_Null() {
        // Arrange, Act and Assert
        Assertions.assertThatThrownBy(() -> new ModelCrawlerClientDefaultImp(null))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("arrowheadHelper cannot be null!");
    }

    @Test
    public void startCrawler_Proceeds_Orchestration_Via_ArrowheadHelper() {
        // Arrange
        OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
        mockOrchestrationResult.setMetadata(new HashMap<>());
        Mockito.when(mockArrowheadHelper.proceedOrchestration(Mockito.any()))
                .thenReturn(createValidOrchestrationResponseDTO(mockOrchestrationResult));

        ModelCrawlerClientDefaultImp client = new ModelCrawlerClientDefaultImp(mockArrowheadHelper);

        // Act
        CrawlerOptionsDTO crawlerOptions = new CrawlerOptionsDTO();
        client.startCrawlerTask(crawlerOptions);

        // Assert
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(mockArrowheadHelper)
                .proceedOrchestration(argumentCaptor.capture());
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
        OrchestrationResponseDTO mockOrchestrationResponse = createValidOrchestrationResponseDTO();
        Mockito.when(mockArrowheadHelper.proceedOrchestration(Mockito.any()))
                .thenReturn(mockOrchestrationResponse);

        ModelCrawlerClientDefaultImp client = new ModelCrawlerClientDefaultImp(mockArrowheadHelper);

        // Act
        CrawlerOptionsDTO crawlerOptions = new CrawlerOptionsDTO();
        client.startCrawlerTask(crawlerOptions);

        // Assert
        ArgumentCaptor<OrchestrationResponseDTO> argumentCaptor = ArgumentCaptor
                .forClass(OrchestrationResponseDTO.class);
        Mockito.verify(mockArrowheadHelper)
                .validateOrchestrationResponse(argumentCaptor.capture());
        OrchestrationResponseDTO actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isEqualTo(mockOrchestrationResponse);
    }

    @Test
    public void startCrawlerTask_Consumes_HttpService_With_Given_Parameters_And_Orchestration_Result_Via_ArrowheadHelper() {
        // Arrange
        OrchestrationResponseDTO mockOrchestrationResponse = createValidOrchestrationResponseDTO();
        Mockito.when(mockArrowheadHelper.proceedOrchestration(Mockito.any()))
                .thenReturn(mockOrchestrationResponse);

        ModelCrawlerClientDefaultImp client = new ModelCrawlerClientDefaultImp(mockArrowheadHelper);

        // Act
        CrawlerOptionsDTO crawlerOptions = new CrawlerOptionsDTO();
        client.startCrawlerTask(crawlerOptions);

        // Assert
        Mockito.verify(mockArrowheadHelper, Mockito.times(1))
                .consumeServiceHTTPByOrchestrationResult(
                        Mockito.eq(mockOrchestrationResponse.getResponse().get(0)),
                        Mockito.eq(Void.class), Mockito.eq(crawlerOptions),
                        Mockito.eq((String[]) null));
    }

    @Test
    public void stopCrawlerTask_Proceed_Orchestration_Via_ArrowheadHelper() {
        // Arrange
        OrchestrationResultDTO mockOrchestrationResult = new OrchestrationResultDTO();
        mockOrchestrationResult.setMetadata(new HashMap<>());
        Mockito.when(mockArrowheadHelper.proceedOrchestration(Mockito.any()))
                .thenReturn(createValidOrchestrationResponseDTO(mockOrchestrationResult));

        ModelCrawlerClientDefaultImp client = new ModelCrawlerClientDefaultImp(mockArrowheadHelper);

        // Act
        client.stopCrawlerTask();

        // Assert
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(mockArrowheadHelper)
                .proceedOrchestration(argumentCaptor.capture());
        String actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isEqualTo("model-crawler-stop");
    }

    @Test
    public void stopCrawlerTask_Validate_Orchestration_Result_Via_ArrowheadHelper() {
        // Arrange
        OrchestrationResponseDTO mockOrchestrationResponse = createValidOrchestrationResponseDTO();
        Mockito.when(mockArrowheadHelper.proceedOrchestration(Mockito.any()))
                .thenReturn(mockOrchestrationResponse);

        ModelCrawlerClientDefaultImp client = new ModelCrawlerClientDefaultImp(mockArrowheadHelper);

        // Act
        client.stopCrawlerTask();

        // Assert
        ArgumentCaptor<OrchestrationResponseDTO> argumentCaptor = ArgumentCaptor
                .forClass(OrchestrationResponseDTO.class);
        Mockito.verify(mockArrowheadHelper)
                .validateOrchestrationResponse(argumentCaptor.capture());
        OrchestrationResponseDTO actualInput = argumentCaptor.getValue();

        Assertions.assertThat(actualInput).isEqualTo(mockOrchestrationResponse);
    }

    @Test
    public void stopCrawlerTask_Consumes_HttpService_With_Given_Parameters_And_Orchestration_Result_Via_ArrowheadHelper() {
        // Arrange
        OrchestrationResponseDTO mockOrchestrationResponse = createValidOrchestrationResponseDTO();
        Mockito.when(mockArrowheadHelper.proceedOrchestration(Mockito.any()))
                .thenReturn(mockOrchestrationResponse);

        ModelCrawlerClientDefaultImp client = new ModelCrawlerClientDefaultImp(mockArrowheadHelper);

        // Act
        client.stopCrawlerTask();

        // Assert
        Mockito.verify(mockArrowheadHelper, Mockito.times(1))
                .consumeServiceHTTPByOrchestrationResult(
                        Mockito.eq(mockOrchestrationResponse.getResponse().get(0)),
                        Mockito.eq(Void.class), Mockito.eq(null),
                        Mockito.eq((String[]) null));
    }
}
