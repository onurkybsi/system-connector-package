package nl.tue.systemconnectorpackage.clients.maas.implementations;

import eu.arrowhead.common.dto.shared.*;
import nl.tue.systemconnectorpackage.clients.maas.models.CreateModelDTO;
import nl.tue.systemconnectorpackage.clients.maas.models.Domain;
import nl.tue.systemconnectorpackage.clients.maas.models.ModelDTO;
import nl.tue.systemconnectorpackage.common.HttpUtilityService;
import nl.tue.systemconnectorpackage.common.JsonUtilities;
import nl.tue.systemconnectorpackage.common.ValidationUtilities;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.ArrowheadHelper;
import nl.tue.systemconnectorpackage.clients.maas.RepositoryManagerClient;
import nl.tue.systemconnectorpackage.common.models.MultipartFormDataDescription;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Default implementation of RepositoryManagerClient interface
 */
public class RepositoryManagerClientDefaultImp implements RepositoryManagerClient {
    private ArrowheadHelper arrowheadHelper;
    private HttpUtilityService httpUtilityService;

    public RepositoryManagerClientDefaultImp(ArrowheadHelper arrowheadHelper,
            HttpUtilityService httpUtilityService) {
        if (ValidationUtilities.containsNull(arrowheadHelper, httpUtilityService))
            throw new InvalidParameterException("arrowheadHelper and httpUtilityService cannot be null!");
        this.arrowheadHelper = arrowheadHelper;
        this.httpUtilityService = httpUtilityService;
    }

    @Override
    public HashMap<String, Object> getDomains(String name, int offset, int limit) {
        return executeGetListServiceFromRepositoryManager("get-domains", name, offset, limit);
    }

    private HashMap<String, Object> executeGetListServiceFromRepositoryManager(String serviceName,
            String name, int offset, int limit) {
        OrchestrationResponseDTO orchestrationResponse = arrowheadHelper.proceedOrchestration(serviceName);
        arrowheadHelper.validateOrchestrationResponse(orchestrationResponse);

        final OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);
        final String[] queryParams = { orchestrationResult.getMetadata().get("request-param-name"), name,
                orchestrationResult.getMetadata().get("request-param-offset"), Integer.toString(offset),
                orchestrationResult.getMetadata().get("request-param-limit"), Integer.toString(limit) };

        return arrowheadHelper.consumeServiceHTTPByOrchestrationResult(orchestrationResult,
                HashMap.class, null, queryParams);
    }

    @Override
    public HashMap<String, Object> createDomain(Domain domainData, MultipartFile file)
            throws IOException {
        return executeCreateServiceFromRepositoryManager("create-domain", domainData, file);
    }

    private <T> HashMap<String, Object> executeCreateServiceFromRepositoryManager(String serviceName, T data,
            MultipartFile file) throws IOException {
        String dataFileName = serviceName.endsWith("model") ? "model" : "domain";

        OrchestrationResponseDTO orchestrationResponse = arrowheadHelper.proceedOrchestration(serviceName);
        arrowheadHelper.validateOrchestrationResponse(orchestrationResponse);

        final OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);
        final String token = arrowheadHelper
                .extractAuthorizationTokenFromOrchestrationResult(orchestrationResult); /*
                                                                                         * TO-DO: Add to
                                                                                         * request!
                                                                                         */
        return sendHttpRequestToCreateRepositoryManagerEntity(orchestrationResult, data, dataFileName, file,
                token);
    }

    private <T> HashMap<String, Object> sendHttpRequestToCreateRepositoryManagerEntity(
            OrchestrationResultDTO orchestrationResult,
            T data,
            String dataName,
            MultipartFile file, String token)
            throws IOException {
        String uriOfService = String.format("%s%s:%s%s", getHttpPrefixByInterfaceName(),
                orchestrationResult.getProvider().getAddress(),
                orchestrationResult.getProvider().getPort(), orchestrationResult.getServiceUri());
        return httpUtilityService.sendHttpPostRequestWithMultipartFormData(uriOfService, new HashMap<>() {
            {
                put("token", token);
            }
        },
                new ArrayList<>() {
                    {
                        add(new MultipartFormDataDescription(dataName,
                                JsonUtilities.convertToJsonString(data),
                                ".json"));
                        add(new MultipartFormDataDescription("file",
                                new String(file.getBytes(), StandardCharsets.UTF_8),
                                FilenameUtils.getExtension(file.getName())));
                    }
                }, HashMap.class);
    }

    private String getHttpPrefixByInterfaceName() {
        return !arrowheadHelper.getInterfaceName().contains("INSECURE")
                ? "https://"
                : "http://";
    }

    @Override
    public Domain updateDomain(Domain newDomain, String id) {
        OrchestrationResponseDTO orchestrationResponse = arrowheadHelper.proceedOrchestration("update-domain");
        arrowheadHelper.validateOrchestrationResponse(orchestrationResponse);

        final OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);
        final String token = arrowheadHelper
                .extractAuthorizationTokenFromOrchestrationResult(orchestrationResult);

        return arrowheadHelper.consumeServiceHTTP(Domain.class,
                HttpMethod.valueOf(orchestrationResult.getMetadata().get("http-method")),
                orchestrationResult.getProvider().getAddress(),
                orchestrationResult.getProvider().getPort(),
                String.format("%s/%s", orchestrationResult.getServiceUri(), id),
                arrowheadHelper.getInterfaceName(), token, newDomain, (String[]) null);
    }

    @Override
    public void deleteDomain(String id) {
        executeDeleteServiceFromRepositoryManager("delete-domain", id);
    }

    private void executeDeleteServiceFromRepositoryManager(String deletingServiceName, String toDeleteDataId) {
        OrchestrationResponseDTO orchestrationResponse = arrowheadHelper
                .proceedOrchestration(deletingServiceName);
        arrowheadHelper.validateOrchestrationResponse(orchestrationResponse);

        final OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);
        final String token = arrowheadHelper
                .extractAuthorizationTokenFromOrchestrationResult(orchestrationResult);

        arrowheadHelper.consumeServiceHTTP(Void.class,
                HttpMethod.valueOf(orchestrationResult.getMetadata().get("http-method")),
                orchestrationResult.getProvider().getAddress(),
                orchestrationResult.getProvider().getPort(),
                String.format("%s/%s", orchestrationResult.getServiceUri(), toDeleteDataId),
                arrowheadHelper.getInterfaceName(), token, null, (String[]) null);
    }

    @Override
    public HashMap<String, Object> getModels(String name, int offset, int limit) {
        return executeGetListServiceFromRepositoryManager("get-models", name, offset, limit);
    }

    @Override
    public HashMap<String, Object> createModel(CreateModelDTO modelDTO, MultipartFile file)
            throws IOException {
        return executeCreateServiceFromRepositoryManager("create-model", modelDTO, file);
    }

    @Override
    public ModelDTO updateModel(CreateModelDTO modelDTO, MultipartFile file, final String id) throws IOException {
        OrchestrationResponseDTO orchestrationResponse = arrowheadHelper.proceedOrchestration("update-model");
        arrowheadHelper.validateOrchestrationResponse(orchestrationResponse);

        final OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);
        final String token = arrowheadHelper
                .extractAuthorizationTokenFromOrchestrationResult(orchestrationResult);
        String uriOfService = String.format("%s%s:%s%s/%s", getHttpPrefixByInterfaceName(),
                orchestrationResult.getProvider().getAddress(),
                orchestrationResult.getProvider().getPort(), orchestrationResult.getServiceUri(), id);

        return httpUtilityService.sendHttpPutRequestWithMultipartFormData(uriOfService, new HashMap<>() {
            {
                put("token", token);
            }
        }, new ArrayList<>() {
            {
                add(new MultipartFormDataDescription("model",
                        JsonUtilities.convertToJsonString(modelDTO),
                        ".json"));
                add(new MultipartFormDataDescription("file",
                        new String(file.getBytes(), StandardCharsets.UTF_8),
                        FilenameUtils.getExtension(file.getName())));
            }
        }, ModelDTO.class);
    }

    @Override
    public void deleteModel(String id) {
        executeDeleteServiceFromRepositoryManager("delete-model", id);
    }
}