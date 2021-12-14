package nl.tue.systemconnectorpackage.clients.maas;

/**
 * Represents client module for MAAS system
 */
public interface MAASClient {
    /**
     * Receives client module for repository-manager which is subsystem of MAAS
     *
     * @return
     */
    RepositoryManagerClient getRepositoryManagerClient();

    /**
     * Receives client module for model-filterer which is subsystem of MAAS
     *
     * @return
     */
    ModelFilterClient getModelFilterClient();

    /**
     * Receives client module for model-transformer which is subsystem of MAAS
     *
     * @return
     */
    ModelTransformerClient getModelTransformerClient();

    /**
     * Receives client module for model-crawler which is subsystem of MAAS
     *
     * @return
     */
    ModelCrawlerClient getModelCrawlerClient();
}