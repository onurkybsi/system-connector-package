package nl.tue.systemconnectorpackage.clients.maas.implementations;

import eu.arrowhead.common.dto.shared.OrchestrationResponseDTO;
import eu.arrowhead.common.dto.shared.OrchestrationResultDTO;
import nl.tue.systemconnectorpackage.clients.maas.ModelCrawlerClient;
import nl.tue.systemconnectorpackage.clients.maas.models.CrawlerOptionsDTO;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.ArrowheadHelper;
import nl.tue.systemconnectorpackage.common.ValidationUtilities;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;

/**
 * Default implementation of ModelCrawlerClient interface
 */
public class ModelCrawlerClientDefaultImp implements ModelCrawlerClient {
        private ArrowheadHelper arrowheadHelper;

        public ModelCrawlerClientDefaultImp(ArrowheadHelper arrowheadHelper) {
                if (ValidationUtilities.containsNull(arrowheadHelper))
                        throw new InvalidParameterException("arrowheadHelper cannot be null!");
                this.arrowheadHelper = arrowheadHelper;
        }

        @Override
        public void startCrawlerTask(CrawlerOptionsDTO options) {
                OrchestrationResponseDTO orchestrationResponse = arrowheadHelper.proceedOrchestration("model-crawler");
                arrowheadHelper.validateOrchestrationResponse(orchestrationResponse);

                final OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);

                arrowheadHelper.consumeServiceHTTPByOrchestrationResult(orchestrationResult,
                                Void.class, options, (String[]) null);
        }

        @Override
        public void stopCrawlerTask() {
                OrchestrationResponseDTO orchestrationResponse = arrowheadHelper
                                .proceedOrchestration("model-crawler-stop");
                arrowheadHelper.validateOrchestrationResponse(orchestrationResponse);

                final OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);

                arrowheadHelper.consumeServiceHTTPByOrchestrationResult(orchestrationResult,
                                Void.class, null, (String[]) null);
        }
}
