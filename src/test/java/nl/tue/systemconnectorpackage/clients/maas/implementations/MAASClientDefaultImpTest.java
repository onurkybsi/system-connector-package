package nl.tue.systemconnectorpackage.clients.maas.implementations;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import eu.arrowhead.common.exception.UnavailableServerException;
import nl.tue.systemconnectorpackage.clients.maas.ModelFilterClient;
import nl.tue.systemconnectorpackage.clients.maas.RepositoryManagerClient;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.ArrowheadHelper;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;
import nl.tue.systemconnectorpackage.clients.maas.ModelTransformerClient;
import nl.tue.systemconnectorpackage.clients.maas.ModelCrawlerClient;

public class MAASClientDefaultImpTest {
    @Test
    public void MAASClientDefaultImp_Throws_InvalidParameterException_If_Given_ArrowheadHelper_Parameter_Is_Null() {
        // Arrange
        RepositoryManagerClient mockRepositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelFilterClient mockModelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelTransformerClient mockModelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        String mockSystemDefinitionListResourcePath = "systemDefinitionListResourcePath";
        // Act and Assert
        Assertions.assertThatThrownBy(() -> new MAASClientDefaultImp(null, mockRepositoryManagerClient,
                mockModelFilterClient, mockModelTransformerClient, modelCrawlerClient,
                mockSystemDefinitionListResourcePath))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("MAASClientDefaultImp parameters are not valid!");
    }

    @Test
    public void MAASClientDefaultImp_Throws_InvalidParameterException_If_Given_RepositoryManagerClient_Parameter_Is_Null() {
        // Arrange
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        ModelFilterClient mockModelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelTransformerClient mockModelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        String mockSystemDefinitionListResourcePath = "systemDefinitionListResourcePath";
        // Act and Assert
        Assertions.assertThatThrownBy(() -> new MAASClientDefaultImp(mockArrowheadHelper, null,
                mockModelFilterClient, mockModelTransformerClient, modelCrawlerClient,
                mockSystemDefinitionListResourcePath))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("MAASClientDefaultImp parameters are not valid!");
    }

    @Test
    public void MAASClientDefaultImp_Throws_InvalidParameterException_If_Given_ModelFilterClient_Parameter_Is_Null() {
        // Arrange
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        RepositoryManagerClient mockRepositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelTransformerClient mockModelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        String mockSystemDefinitionListResourcePath = "systemDefinitionListResourcePath";
        // Act and Assert
        Assertions.assertThatThrownBy(() -> new MAASClientDefaultImp(mockArrowheadHelper, mockRepositoryManagerClient,
                null, mockModelTransformerClient, modelCrawlerClient, mockSystemDefinitionListResourcePath))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("MAASClientDefaultImp parameters are not valid!");
    }

    @Test
    public void MAASClientDefaultImp_Throws_InvalidParameterException_If_Given_ModelTransformerClient_Parameter_Is_Null() {
        // Arrange
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        RepositoryManagerClient mockRepositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelFilterClient mockModelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        String mockSystemDefinitionListResourcePath = "systemDefinitionListResourcePath";
        // Act and Assert
        Assertions.assertThatThrownBy(() -> new MAASClientDefaultImp(mockArrowheadHelper, mockRepositoryManagerClient,
                mockModelFilterClient, null, modelCrawlerClient, mockSystemDefinitionListResourcePath))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("MAASClientDefaultImp parameters are not valid!");
    }

    @Test
    public void MAASClientDefaultImp_Throws_InvalidParameterException_If_Given_ModelCrawlerClient_Parameter_Is_Null() {
        // Arrange
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        RepositoryManagerClient mockRepositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelFilterClient mockModelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelTransformerClient mockModelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        String mockSystemDefinitionListResourcePath = "systemDefinitionListResourcePath";
        // Act and Assert
        Assertions.assertThatThrownBy(() -> new MAASClientDefaultImp(mockArrowheadHelper, mockRepositoryManagerClient,
                mockModelFilterClient, mockModelTransformerClient, null, mockSystemDefinitionListResourcePath))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("MAASClientDefaultImp parameters are not valid!");
    }

    @Test
    public void MAASClientDefaultImp_Throws_InvalidParameterException_If_Given_SystemDefinitionListResourcePath_Parameter_Is_Null() {
        // Arrange
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        RepositoryManagerClient mockRepositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelFilterClient mockModelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelTransformerClient mockModelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        // Act and Assert
        Assertions.assertThatThrownBy(() -> new MAASClientDefaultImp(mockArrowheadHelper, mockRepositoryManagerClient,
                mockModelFilterClient, mockModelTransformerClient, modelCrawlerClient, null))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("MAASClientDefaultImp parameters are not valid!");
    }

    @Test
    public void MAASClientDefaultImp_Throws_InvalidParameterException_If_Given_SystemDefinitionListResourcePath_Parameter_Is_Empty() {
        // Arrange
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        RepositoryManagerClient mockRepositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelFilterClient mockModelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelTransformerClient mockModelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        // Act and Assert
        Assertions.assertThatThrownBy(() -> new MAASClientDefaultImp(mockArrowheadHelper, mockRepositoryManagerClient,
                mockModelFilterClient, mockModelTransformerClient, modelCrawlerClient, null))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("MAASClientDefaultImp parameters are not valid!");
    }

    @Test
    public void MAASClientDefaultImp_Registers_SystemsToArrowhead_Via_ArrowheadHelper()
            throws UnavailableServerException, JsonSyntaxException, IOException {
        // Arrange
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        RepositoryManagerClient mockRepositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelFilterClient mockModelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelTransformerClient mockModelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        String mockSystemDefinitionListResourcePath = "systemDefinitionListResourcePath";

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.doNothing().when(mockArrowheadHelper).registerSystemsToArrowhead(argumentCaptor.capture());
        // Act
        new MAASClientDefaultImp(mockArrowheadHelper, mockRepositoryManagerClient,
                mockModelFilterClient, mockModelTransformerClient, modelCrawlerClient,
                mockSystemDefinitionListResourcePath);
        // Assert
        boolean callingCount = argumentCaptor.getAllValues().size() == 1;
        boolean calledParameterIsExpected = argumentCaptor.getAllValues()
                .get(0) == mockSystemDefinitionListResourcePath;
        Assertions.assertThat(callingCount && calledParameterIsExpected).isTrue();
    }

    @Test
    public void MAASClientDefaultImp_Set_RepositoryManagerClient()
            throws UnavailableServerException, JsonSyntaxException, IOException {
        // Arrange
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        RepositoryManagerClient mockRepositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelFilterClient mockModelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelTransformerClient mockModelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        String mockSystemDefinitionListResourcePath = "systemDefinitionListResourcePath";
        // Act
        MAASClientDefaultImp imp = new MAASClientDefaultImp(mockArrowheadHelper, mockRepositoryManagerClient,
                mockModelFilterClient, mockModelTransformerClient, modelCrawlerClient,
                mockSystemDefinitionListResourcePath);
        // Assert
        Assertions.assertThat(imp.getRepositoryManagerClient())
                .isEqualTo(mockRepositoryManagerClient);
    }

    @Test
    public void MAASClientDefaultImp_Set_ModelFilterClient()
            throws UnavailableServerException, JsonSyntaxException, IOException {
        // Arrange
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        RepositoryManagerClient mockRepositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelFilterClient mockModelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelTransformerClient mockModelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        String mockSystemDefinitionListResourcePath = "systemDefinitionListResourcePath";
        // Act
        MAASClientDefaultImp imp = new MAASClientDefaultImp(mockArrowheadHelper, mockRepositoryManagerClient,
                mockModelFilterClient, mockModelTransformerClient, modelCrawlerClient,
                mockSystemDefinitionListResourcePath);
        // Assert
        Assertions.assertThat(imp.getModelFilterClient())
                .isEqualTo(mockModelFilterClient);
    }

    @Test
    public void MAASClientDefaultImp_Set_ModelTransformerClient()
            throws UnavailableServerException, JsonSyntaxException, IOException {
        // Arrange
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        RepositoryManagerClient mockRepositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelFilterClient mockModelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelTransformerClient mockModelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        String mockSystemDefinitionListResourcePath = "systemDefinitionListResourcePath";
        // Act
        MAASClientDefaultImp imp = new MAASClientDefaultImp(mockArrowheadHelper, mockRepositoryManagerClient,
                mockModelFilterClient, mockModelTransformerClient, modelCrawlerClient,
                mockSystemDefinitionListResourcePath);
        // Assert
        Assertions.assertThat(imp.getModelTransformerClient())
                .isEqualTo(mockModelTransformerClient);
    }

    @Test
    public void MAASClientDefaultImp_Set_ModelCrawlerClient()
            throws UnavailableServerException, JsonSyntaxException, IOException {
        // Arrange
        ArrowheadHelper mockArrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        RepositoryManagerClient mockRepositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelFilterClient mockModelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelTransformerClient mockModelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        String mockSystemDefinitionListResourcePath = "systemDefinitionListResourcePath";
        // Act
        MAASClientDefaultImp imp = new MAASClientDefaultImp(mockArrowheadHelper, mockRepositoryManagerClient,
                mockModelFilterClient, mockModelTransformerClient, modelCrawlerClient,
                mockSystemDefinitionListResourcePath);
        // Assert
        Assertions.assertThat(imp.getModelCrawlerClient())
                .isEqualTo(modelCrawlerClient);
    }
}
