package nl.tue.systemconnectorpackage.clients.xama.implementations;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import eu.arrowhead.common.dto.shared.OrchestrationResponseDTO;
import eu.arrowhead.common.dto.shared.OrchestrationResultDTO;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.ArrowheadHelper;
import nl.tue.systemconnectorpackage.clients.xama.XAMAClient;
import nl.tue.systemconnectorpackage.clients.xama.models.Match;
import nl.tue.systemconnectorpackage.clients.xama.models.Model;
import nl.tue.systemconnectorpackage.common.HttpUtilityService;
import nl.tue.systemconnectorpackage.common.models.MultipartFormDataDescription;

/**
 * Default implementation of XAMAClient
 */
public class XAMAClientDefaultImp implements XAMAClient {
    private ArrowheadHelper arrowheadHelper;
    private HttpUtilityService httpUtilityService;

    public XAMAClientDefaultImp(ArrowheadHelper arrowheadHelper, HttpUtilityService httpUtilityService) {
        this.arrowheadHelper = arrowheadHelper;
        this.httpUtilityService = httpUtilityService;
    }

    @Override
    public ArrayList<Match> findPossibleRelationts(MultipartFile file, String locationRepository) throws IOException {
        OrchestrationResponseDTO orchestrationResponse = arrowheadHelper
                .proceedOrchestration("find-possible-relations");
        arrowheadHelper.validateOrchestrationResponse(orchestrationResponse);

        final OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);

        String uriOfService = String.format("%s%s:%s%s?locationRepository=%s",
                arrowheadHelper.getHttpPrefixByInterfaceName(),
                orchestrationResult.getProvider().getAddress(),
                orchestrationResult.getProvider().getPort(), orchestrationResult.getServiceUri(), locationRepository);
        String authTokenOfTheService = arrowheadHelper
                .extractAuthorizationTokenFromOrchestrationResult(orchestrationResult);

        return (ArrayList<Match>) httpUtilityService.sendHttpPostRequestWithMultipartFormData(uriOfService,
                new HashMap<>() {
                    {
                        put("token", authTokenOfTheService);
                    }
                }, new ArrayList<>() {
                    {
                        add(new MultipartFormDataDescription("file",
                                new String(file.getBytes(), StandardCharsets.UTF_8),
                                FilenameUtils.getExtension(file.getName())));
                    }
                }, ArrayList.class);
    }

    @Override
    public Model createNewModel(String name, String type, String fileURL, boolean isLocal) {
        OrchestrationResponseDTO orchestrationResponse = arrowheadHelper
                .proceedOrchestration("create-new-model-xama"); /** Should be declare as constants */
        arrowheadHelper.validateOrchestrationResponse(orchestrationResponse);

        final OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);

        final String[] queryParams = { orchestrationResult.getMetadata().get("request-param-name"), name,
                orchestrationResult.getMetadata().get("request-param-type"), type,
                orchestrationResult.getMetadata().get("request-param-fileURL"), fileURL,
                orchestrationResult.getMetadata().get("request-param-isLocal"), isLocal ? "true" : "false"
        };

        return arrowheadHelper.consumeServiceHTTPByOrchestrationResult(orchestrationResult,
                Model.class, null, queryParams);
    }
}
