package nl.tue.systemconnectorpackage.clients.maas.implementations;

import eu.arrowhead.common.dto.shared.OrchestrationResponseDTO;
import eu.arrowhead.common.dto.shared.OrchestrationResultDTO;
import nl.tue.systemconnectorpackage.clients.maas.ModelTransformerClient;
import nl.tue.systemconnectorpackage.clients.maas.models.TransformationListDTO;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.ArrowheadHelper;
import nl.tue.systemconnectorpackage.common.ValidationUtilities;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;

/**
 * Default implementation of ModelTransformerClient interface
 */
public class ModelTransformerClientDefaultImp implements ModelTransformerClient {
        private ArrowheadHelper arrowheadHelper;

        public ModelTransformerClientDefaultImp(ArrowheadHelper arrowheadHelper) {
                if (ValidationUtilities.containsNull(arrowheadHelper))
                        throw new InvalidParameterException("arrowheadHelper cannot be null!");
                this.arrowheadHelper = arrowheadHelper;
        }

        @Override
        public TransformationListDTO transformModel(String format) {
                OrchestrationResponseDTO orchestrationResponse = arrowheadHelper
                                .proceedOrchestration("model-transformer");
                arrowheadHelper.validateOrchestrationResponse(orchestrationResponse);

                OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);

                final String[] queryParams = { orchestrationResult.getMetadata().get("request-param-format"), format };

                return arrowheadHelper.consumeServiceHTTPByOrchestrationResult(orchestrationResult,
                                TransformationListDTO.class, null, queryParams);
        }
}
