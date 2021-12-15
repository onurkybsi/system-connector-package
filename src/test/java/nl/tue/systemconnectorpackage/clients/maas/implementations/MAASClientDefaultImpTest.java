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
    public void MAASClientDefaultImpTest_Throws_InvalidParameterException_If_Given_ArrowheadHelper_Parameter_Is_Null() {
        // Arrange
        RepositoryManagerClient repositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelFilterClient modelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelTransformerClient modelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        String systemDefinitionListResourcePath = "systemDefinitionListResourcePath";
        // Act and Assert
        Assertions.assertThatThrownBy(() -> new MAASClientDefaultImp(null, repositoryManagerClient,
                modelFilterClient, modelTransformerClient, modelCrawlerClient, systemDefinitionListResourcePath))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("MAASClientDefaultImp parameters are not valid!");
    }

    @Test
    public void MAASClientDefaultImpTest_Throws_InvalidParameterException_If_Given_RepositoryManagerClient_Parameter_Is_Null() {
        // Arrange
        ArrowheadHelper arrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        ModelFilterClient modelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelTransformerClient modelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        String systemDefinitionListResourcePath = "systemDefinitionListResourcePath";
        // Act and Assert
        Assertions.assertThatThrownBy(() -> new MAASClientDefaultImp(arrowheadHelper, null,
                modelFilterClient, modelTransformerClient, modelCrawlerClient, systemDefinitionListResourcePath))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("MAASClientDefaultImp parameters are not valid!");
    }

    @Test
    public void MAASClientDefaultImpTest_Throws_InvalidParameterException_If_Given_ModelFilterClient_Parameter_Is_Null() {
        // Arrange
        ArrowheadHelper arrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        RepositoryManagerClient repositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelTransformerClient modelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        String systemDefinitionListResourcePath = "systemDefinitionListResourcePath";
        // Act and Assert
        Assertions.assertThatThrownBy(() -> new MAASClientDefaultImp(arrowheadHelper, repositoryManagerClient,
                null, modelTransformerClient, modelCrawlerClient, systemDefinitionListResourcePath))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("MAASClientDefaultImp parameters are not valid!");
    }

    @Test
    public void MAASClientDefaultImpTest_Throws_InvalidParameterException_If_Given_ModelTransformerClient_Parameter_Is_Null() {
        // Arrange
        ArrowheadHelper arrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        RepositoryManagerClient repositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelFilterClient modelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        String systemDefinitionListResourcePath = "systemDefinitionListResourcePath";
        // Act and Assert
        Assertions.assertThatThrownBy(() -> new MAASClientDefaultImp(arrowheadHelper, repositoryManagerClient,
                modelFilterClient, null, modelCrawlerClient, systemDefinitionListResourcePath))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("MAASClientDefaultImp parameters are not valid!");
    }

    @Test
    public void MAASClientDefaultImpTest_Throws_InvalidParameterException_If_Given_ModelCrawlerClient_Parameter_Is_Null() {
        // Arrange
        ArrowheadHelper arrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        RepositoryManagerClient repositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelFilterClient modelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelTransformerClient modelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        String systemDefinitionListResourcePath = "systemDefinitionListResourcePath";
        // Act and Assert
        Assertions.assertThatThrownBy(() -> new MAASClientDefaultImp(arrowheadHelper, repositoryManagerClient,
                modelFilterClient, modelTransformerClient, null, systemDefinitionListResourcePath))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("MAASClientDefaultImp parameters are not valid!");
    }

    @Test
    public void MAASClientDefaultImpTest_Throws_InvalidParameterException_If_Given_SystemDefinitionListResourcePath_Parameter_Is_Null() {
        // Arrange
        ArrowheadHelper arrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        RepositoryManagerClient repositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelFilterClient modelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelTransformerClient modelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        // Act and Assert
        Assertions.assertThatThrownBy(() -> new MAASClientDefaultImp(arrowheadHelper, repositoryManagerClient,
                modelFilterClient, modelTransformerClient, modelCrawlerClient, null))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("MAASClientDefaultImp parameters are not valid!");
    }

    @Test
    public void MAASClientDefaultImpTest_Throws_InvalidParameterException_If_Given_SystemDefinitionListResourcePath_Parameter_Is_Empty() {
        // Arrange
        ArrowheadHelper arrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        RepositoryManagerClient repositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelFilterClient modelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelTransformerClient modelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        // Act and Assert
        Assertions.assertThatThrownBy(() -> new MAASClientDefaultImp(arrowheadHelper, repositoryManagerClient,
                modelFilterClient, modelTransformerClient, modelCrawlerClient, null))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("MAASClientDefaultImp parameters are not valid!");
    }

    @Test
    public void MAASClientDefaultImpTest_Registers_SystemsToArrowhead_Via_ArrowheadHelper()
            throws UnavailableServerException, JsonSyntaxException, IOException {
        // Arrange
        ArrowheadHelper arrowheadHelper = Mockito.mock(ArrowheadHelper.class);
        RepositoryManagerClient repositoryManagerClient = Mockito.mock(RepositoryManagerClient.class);
        ModelFilterClient modelFilterClient = Mockito.mock(ModelFilterClient.class);
        ModelTransformerClient modelTransformerClient = Mockito.mock(ModelTransformerClient.class);
        ModelCrawlerClient modelCrawlerClient = Mockito.mock(ModelCrawlerClient.class);
        String systemDefinitionListResourcePath = "systemDefinitionListResourcePath";

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.doNothing().when(arrowheadHelper).registerSystemsToArrowhead(argumentCaptor.capture());
        // Act
        new MAASClientDefaultImp(arrowheadHelper, repositoryManagerClient,
                modelFilterClient, modelTransformerClient, modelCrawlerClient, systemDefinitionListResourcePath);
        // Assert
        boolean callingCount = argumentCaptor.getAllValues().size() == 1;
        boolean calledParameterIsExpected = argumentCaptor.getAllValues().get(0) == systemDefinitionListResourcePath;
        Assertions.assertThat(callingCount && calledParameterIsExpected).isTrue();
    }

}
