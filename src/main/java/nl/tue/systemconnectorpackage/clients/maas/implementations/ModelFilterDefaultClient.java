package nl.tue.systemconnectorpackage.clients.maas.implementations;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;

import eu.arrowhead.common.dto.shared.OrchestrationResponseDTO;
import eu.arrowhead.common.dto.shared.OrchestrationResultDTO;
import nl.tue.systemconnectorpackage.clients.maas.ModelFilterClient;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.ArrowheadHelper;
import nl.tue.systemconnectorpackage.common.ValidationUtilities;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;

/**
 * Default implementation of ModelFilterClient interface
 */
public class ModelFilterDefaultClient implements ModelFilterClient {
        private ArrowheadHelper arrowheadHelper;

        public ModelFilterDefaultClient(ArrowheadHelper arrowheadHelper) {
                if (ValidationUtilities.containsNull(arrowheadHelper))
                        throw new InvalidParameterException("arrowheadHelper cannot be null");
                this.arrowheadHelper = arrowheadHelper;
        }

        @Override
        public HashMap<String, Object> filterModel(String name, String format, String elems, String repo) {
                OrchestrationResponseDTO orchestrationResponse = arrowheadHelper.proceedOrchestration("model-filter");
                arrowheadHelper.validateOrchestrationResponse(orchestrationResponse);

                final OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);

                final String[] queryParams = { orchestrationResult.getMetadata().get("request-param-name"), name,
                                orchestrationResult.getMetadata().get("request-param-format"), format,
                                orchestrationResult.getMetadata().get("request-param-elems"), elems,
                                orchestrationResult.getMetadata().get("request-param-repo"), repo, };

                return arrowheadHelper.consumeServiceHTTPByOrchestrationResult(orchestrationResult, HashMap.class, null,
                                queryParams);
        }
}
