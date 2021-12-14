package nl.tue.systemconnectorpackage.clients.maas;

import nl.tue.systemconnectorpackage.clients.maas.models.CrawlerOptionsDTO;

/**
 * Client module for model-crawler which is subsystem of MAAS
 */
public interface ModelCrawlerClient {
    /**
     * Passes the given parameters to the "model-crawler" service of the
     * model-crawler sub-system of MAAS
     *
     * @param options
     * @return Response from MAAS system
     */
    void startCrawlerTask(CrawlerOptionsDTO options);

    /**
     * Passes the given parameters to the "model-crawler-stop" service of the
     * model-crawler sub-system of MAAS
     *
     * @param options
     * @return Response from MAAS system
     */
    void stopCrawlerTask();
}
