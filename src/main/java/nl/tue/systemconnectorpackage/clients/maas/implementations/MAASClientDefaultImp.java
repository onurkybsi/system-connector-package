package nl.tue.systemconnectorpackage.clients.maas.implementations;

import nl.tue.systemconnectorpackage.common.ValidationUtilities;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.ArrowheadHelper;
import nl.tue.systemconnectorpackage.clients.maas.MAASClient;
import nl.tue.systemconnectorpackage.clients.maas.ModelCrawlerClient;
import nl.tue.systemconnectorpackage.clients.maas.ModelFilterClient;
import nl.tue.systemconnectorpackage.clients.maas.ModelTransformerClient;
import nl.tue.systemconnectorpackage.clients.maas.RepositoryManagerClient;

import com.google.gson.JsonSyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.arrowhead.common.exception.UnavailableServerException;

/**
 * Default implementation of MAASClient interface
 */
public class MAASClientDefaultImp implements MAASClient {
    private final Logger logger = LogManager.getLogger(MAASClientDefaultImp.class);

    private RepositoryManagerClient repositoryManagerClient;
    private ModelFilterClient modelFilterClient;
    private ModelTransformerClient modelTransformerClient;
    private ModelCrawlerClient modelCrawlerClient;

    public MAASClientDefaultImp(ArrowheadHelper arrowheadHelper, RepositoryManagerClient repositoryManagerClient,
            ModelFilterClient modelFilterClient, ModelTransformerClient modelTransformerClient,
            ModelCrawlerClient modelCrawlerClient)
            throws UnavailableServerException, JsonSyntaxException {
        if (ValidationUtilities.containsNull(arrowheadHelper, repositoryManagerClient, modelFilterClient,
                modelTransformerClient, modelCrawlerClient))
            throw new InvalidParameterException("MAASClientDefaultImp parameters are not valid!");
        this.repositoryManagerClient = repositoryManagerClient;
        this.modelFilterClient = modelFilterClient;
        this.modelTransformerClient = modelTransformerClient;
        this.modelCrawlerClient = modelCrawlerClient;

        logger.info("MAAS client constructed successfully !");
    }

    @Override
    public RepositoryManagerClient getRepositoryManagerClient() {
        return repositoryManagerClient;
    }

    @Override
    public ModelFilterClient getModelFilterClient() {
        return modelFilterClient;
    }

    @Override
    public ModelTransformerClient getModelTransformerClient() {
        return modelTransformerClient;
    }

    @Override
    public ModelCrawlerClient getModelCrawlerClient() {
        return modelCrawlerClient;
    }
}