package nl.tue.systemconnectorpackage.clients.utilities.arrowhead.implementations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.exceptions.ArrowheadAuthorizationException;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.exceptions.ArrowheadOrchestrationException;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.exceptions.ArrowheadServiceRegistryException;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.models.ArrowheadServiceInformation;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.models.ArrowheadSystemInformation;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.models.AuthorizationIntraCloudListResponseDTO;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.models.AuthorizationIntraCloudRequestDTO;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import eu.arrowhead.client.library.ArrowheadService;
import eu.arrowhead.common.Utilities;
import eu.arrowhead.common.core.CoreSystem;
import eu.arrowhead.common.dto.shared.OrchestrationFlags;
import eu.arrowhead.common.dto.shared.OrchestrationFormRequestDTO;
import eu.arrowhead.common.dto.shared.OrchestrationResponseDTO;
import eu.arrowhead.common.dto.shared.OrchestrationResultDTO;
import eu.arrowhead.common.dto.shared.ServiceQueryFormDTO;
import eu.arrowhead.common.dto.shared.ServiceRegistryRequestDTO;
import eu.arrowhead.common.dto.shared.ServiceRegistryResponseDTO;
import eu.arrowhead.common.dto.shared.ServiceSecurityType;
import eu.arrowhead.common.dto.shared.SystemRequestDTO;
import eu.arrowhead.common.dto.shared.SystemResponseDTO;
import eu.arrowhead.common.exception.UnavailableServerException;
import eu.arrowhead.common.http.HttpService;
import nl.tue.systemconnectorpackage.common.JsonUtilities;
import nl.tue.systemconnectorpackage.common.StringUtilities;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;
import nl.tue.systemconnectorpackage.common.exceptions.UnexpectedException;

public abstract class ArrowheadHelperImpBase {
    // TO-DO: It should be taken from application.properties
    protected boolean tokenSecurityFilterEnabled = false;
    // TO-DO: It should be taken from application.properties
    protected boolean sslEnabled = false;

    @Value("${arrowhead_authorization_address:localhost}")
    protected String authorizationAddress;
    @Value("${arrowhead_authorization_port:8445}")
    protected Integer authorizationPort;
    @Value("${arrowhead_serviceregistry_address:localhost}")
    protected String serviceRegistryAddress;
    @Value("${arrowhead_serviceregistry_port:8443}")
    protected Integer serviceRegistryPort;

    protected static final List<ArrowheadSystemInformation> connectedSystemsLocalRepository = new ArrayList<>();

    protected ArrowheadService arrowheadService;
    protected HttpService httpService;
    protected Logger logger;

    protected ArrowheadHelperImpBase(ArrowheadService arrowheadService, HttpService httpService, Logger logger) {
        validateConstructionParameters(arrowheadService, httpService, logger);
        this.arrowheadService = arrowheadService;
        this.httpService = httpService;
        this.logger = logger;
    }

    private void validateConstructionParameters(final ArrowheadService arrowheadService, final HttpService httpService,
            Logger logger) throws InvalidParameterException {
        if (arrowheadService == null)
            throw new InvalidParameterException("arrowheadService cannot be null!");
        if (httpService == null)
            throw new InvalidParameterException("httpService cannot be null!");
        if (logger == null)
            throw new InvalidParameterException("logger cannot be null!");
    }

    public void registerSystemsToArrowhead(final String systemDefinitionListResourcePath)
            throws UnavailableServerException, IOException, JsonSyntaxException {
        if (!StringUtilities.isValid(systemDefinitionListResourcePath))
            throw new InvalidParameterException("systemDefinitionListResourcePath is not valid!");

        try {
            checkCoreSystemAvailabilities();

            loadSystemInformationListFromResource(systemDefinitionListResourcePath);
            registerProviders(getProviders(connectedSystemsLocalRepository));
            registerConsumers(getConsumers(connectedSystemsLocalRepository));
        } catch (Exception e) {
            logger.error("Error occurred: ", e);
        }
    }

    private void checkCoreSystemAvailabilities() {
        if (!arrowheadService.echoCoreSystem(CoreSystem.SERVICE_REGISTRY))
            throw new UnavailableServerException("CoreSystem.SERVICE_REGISTRY is not available!");

        if (!arrowheadService.echoCoreSystem(CoreSystem.AUTHORIZATION))
            throw new UnavailableServerException("CoreSystem.AUTHORIZATION is not available!");
        arrowheadService.updateCoreServiceURIs(CoreSystem.AUTHORIZATION);
        if (sslEnabled && tokenSecurityFilterEnabled) {
            // setTokenSecurityFilter();
        } else {
            // logger.info("TokenSecurityFilter in not active");
        }

        if (!arrowheadService.echoCoreSystem(CoreSystem.ORCHESTRATOR))
            throw new UnavailableServerException("CoreSystem.ORCHESTRATOR is not available!");
        arrowheadService.updateCoreServiceURIs(CoreSystem.ORCHESTRATOR);
    }

    private void loadSystemInformationListFromResource(final String systemDefinitionListResourcePath)
            throws IOException, JsonSyntaxException {
        File resource = new ClassPathResource(systemDefinitionListResourcePath).getFile();
        String jsonStringOfSystemsToConnect = new String(Files.readAllBytes(resource.toPath()));
        List<ArrowheadSystemInformation> systemsToConnect = JsonUtilities
                .convertFromJsonString(new TypeToken<List<ArrowheadSystemInformation>>() {
                }, jsonStringOfSystemsToConnect);
        if (systemsToConnect == null)
            throw new UnexpectedException("Failed to load systems to connect from source!");

        connectedSystemsLocalRepository.addAll(systemsToConnect);
    }

    private static List<ArrowheadSystemInformation> getProviders(
            final List<ArrowheadSystemInformation> arrowheadSystemInformationList) {
        return arrowheadSystemInformationList
                .stream()
                .filter(si -> !si.getProducedServices().isEmpty())
                .collect(Collectors.toList());
    }

    private void registerProviders(final List<ArrowheadSystemInformation> providers) {
        for (ArrowheadSystemInformation provider : providers) {
            if (provider == null)
                continue;
            registerProviderAndSetItsDetailsInLocalRepository(provider);
        }
    }

    private void registerProviderAndSetItsDetailsInLocalRepository(final ArrowheadSystemInformation provider) {
        SystemRequestDTO systemRequest = createSystemRequestBySystemInformation(provider);

        for (ArrowheadServiceInformation service : provider.getProducedServices()) {
            if (service == null)
                return;

            ServiceRegistryRequestDTO serviceCreationRequest = createServiceRegistrationRequest(systemRequest, service);
            ServiceRegistryResponseDTO response = arrowheadService
                    .forceRegisterServiceToServiceRegistry(serviceCreationRequest);

            setProviderDetailsInLocalRepositoryByServiceRegistrationResponse(provider, service, response);
        }
    }

    protected static SystemRequestDTO createSystemRequestBySystemInformation(
            final ArrowheadSystemInformation systemInformation) {
        SystemRequestDTO request = new SystemRequestDTO();
        request.setSystemName(systemInformation.getSystemName());
        request.setAddress(systemInformation.getAddress());
        request.setPort(systemInformation.getPort());
        return request;
    }

    private ServiceRegistryRequestDTO createServiceRegistrationRequest(final SystemRequestDTO serviceProviderSystem,
            final ArrowheadServiceInformation serviceInformation) {
        ServiceRegistryRequestDTO serviceRegistryRequest = new ServiceRegistryRequestDTO();
        serviceRegistryRequest.setProviderSystem(serviceProviderSystem);
        serviceRegistryRequest.setServiceDefinition(serviceInformation.getName());
        serviceRegistryRequest.setServiceUri(serviceInformation.getUri());
        serviceRegistryRequest.setMetadata(serviceInformation.getMetadata());

        if (tokenSecurityFilterEnabled) {
            serviceProviderSystem.setAuthenticationInfo(
                    Base64.getEncoder().encodeToString(arrowheadService.getMyPublicKey().getEncoded()));
            serviceRegistryRequest.setSecure(ServiceSecurityType.TOKEN.toString());
            serviceRegistryRequest.setInterfaces(List.of(ArrowheadConstants.SECURE_HTTP_SERVICE_INTERFACE));
        } else if (sslEnabled) {
            serviceProviderSystem.setAuthenticationInfo(
                    Base64.getEncoder().encodeToString(arrowheadService.getMyPublicKey().getEncoded()));
            serviceRegistryRequest.setSecure(ServiceSecurityType.CERTIFICATE.toString());
            serviceRegistryRequest.setInterfaces(List.of(ArrowheadConstants.SECURE_HTTP_SERVICE_INTERFACE));
        } else {
            serviceRegistryRequest.setSecure(ServiceSecurityType.NOT_SECURE.toString());
            serviceRegistryRequest.setInterfaces(List.of(ArrowheadConstants.INSECURE_HTTP_SERVICE_INTERFACE));
        }

        return serviceRegistryRequest;
    }

    private static void setProviderDetailsInLocalRepositoryByServiceRegistrationResponse(
            final ArrowheadSystemInformation provider, final ArrowheadServiceInformation serviceToSetDetails,
            final ServiceRegistryResponseDTO response) {
        provider.setId(response.getProvider().getId());
        serviceToSetDetails.setId(response.getServiceDefinition().getId());
        serviceToSetDetails.setProviderId(response.getProvider().getId());
    }

    private static List<ArrowheadSystemInformation> getConsumers(
            final List<ArrowheadSystemInformation> arrowheadSystemInformationList) {
        return arrowheadSystemInformationList
                .stream()
                .filter(si -> !si.getConsumedServiceNames().isEmpty())
                .collect(Collectors.toList());
    }

    protected abstract void registerConsumers(List<ArrowheadSystemInformation> consumers);

    protected SystemResponseDTO sendSystemRegistrationRequest(ArrowheadSystemInformation system) {
        SystemRequestDTO request = createSystemRequestBySystemInformation(system);
        ResponseEntity<SystemResponseDTO> response = httpService.sendRequest(Utilities.createURI(this.getUriScheme(),
                serviceRegistryAddress, serviceRegistryPort,
                ArrowheadConstants.CONSUMER_SYSTEM_REGISTRATION_URI),
                HttpMethod.POST, SystemResponseDTO.class, request);
        if (response == null || response.getStatusCodeValue() != HttpStatus.CREATED.value())
            throw new ArrowheadServiceRegistryException(
                    "Sending system registration request unsuccessful for: " + system.getSystemName());

        return response.getBody();
    }

    protected AuthorizationIntraCloudListResponseDTO sendConsumedServiceRegistrationRequest(
            ArrowheadSystemInformation consumer,
            ArrowheadServiceInformation consumedService) {
        AuthorizationIntraCloudRequestDTO request = createAuthorizationIntraCloudRequestBySystemInformation(consumer,
                consumedService);
        ResponseEntity<AuthorizationIntraCloudListResponseDTO> response = httpService.sendRequest(
                Utilities.createURI(this.getUriScheme(),
                        authorizationAddress, authorizationPort,
                        ArrowheadConstants.CONSUMER_REGISTRATION_URI),
                HttpMethod.POST, AuthorizationIntraCloudListResponseDTO.class, request);
        if (response == null || response.getStatusCodeValue() != HttpStatus.CREATED.value())
            throw new ArrowheadAuthorizationException(
                    "sendConsumedServiceRegistrationRequest unsuccessful for: " + consumer.getSystemName());

        return response.getBody();
    }

    private AuthorizationIntraCloudRequestDTO createAuthorizationIntraCloudRequestBySystemInformation(
            ArrowheadSystemInformation consumer,
            ArrowheadServiceInformation consumedService) {
        return new AuthorizationIntraCloudRequestDTO(consumer.getId(),
                java.util.List.of(consumedService.getProviderId()),
                java.util.List.of(consumedService.getId()), java.util.List.of(Long.valueOf(2)/*
                                                                                              * TO-DO: Get from
                                                                                              * arrowhead!
                                                                                              */));
    }

    protected OrchestrationResponseDTO proceedOrchestration(String serviceName,
            OrchestrationFormRequestDTO.Builder orchestrationRequestBuilder) {
        if (!StringUtilities.isValid(serviceName))
            throw new InvalidParameterException("serviceName is not valid!");

        final ServiceQueryFormDTO serviceQueryForm = new ServiceQueryFormDTO.Builder(serviceName)
                .interfaces(getInterfaceName())
                .build();
        final OrchestrationFormRequestDTO orchestrationFormRequest = orchestrationRequestBuilder
                .requestedService(serviceQueryForm)
                .flag(OrchestrationFlags.Flag.MATCHMAKING, true) /**
                                                                  * TO-DO: I should talk about flags with the team,
                                                                  * Which flags are necessary for us ?
                                                                  */
                .flag(OrchestrationFlags.Flag.OVERRIDE_STORE, true)
                .build();

        return arrowheadService.proceedOrchestration(orchestrationFormRequest);
    }

    public String getInterfaceName() {
        return sslEnabled
                ? "HTTP-SECURE-JSON"
                : "HTTP-INSECURE-JSON";
    }

    public void validateOrchestrationResponse(OrchestrationResponseDTO orchestrationResponse) {
        if (orchestrationResponse == null)
            throw new ArrowheadOrchestrationException("No orchestration response received");
        if (orchestrationResponse.getResponse().isEmpty())
            throw new ArrowheadOrchestrationException("No provider found during the orchestration");
    }

    public <T> T consumeServiceHTTPByOrchestrationResult(OrchestrationResultDTO orchestrationResult,
            Class<T> responseType, Object payload, String... queryParams) {
        if (orchestrationResult == null)
            throw new InvalidParameterException("orchestrationResult cannot be null!");
        if (orchestrationResult.getProvider() == null)
            throw new InvalidParameterException("orchestrationResult is not valid: Provider is null!");
        if (orchestrationResult.getMetadata() == null)
            throw new InvalidParameterException("orchestrationResult is not valid: Metadata is null!");

        return arrowheadService.consumeServiceHTTP(responseType,
                HttpMethod.valueOf(orchestrationResult.getMetadata().get("http-method")),
                orchestrationResult.getProvider().getAddress(),
                orchestrationResult.getProvider().getPort(),
                orchestrationResult.getServiceUri(), getInterfaceName(),
                extractAuthorizationTokenFromOrchestrationResult(orchestrationResult),
                payload, queryParams);
    }

    public String extractAuthorizationTokenFromOrchestrationResult(final OrchestrationResultDTO orchestrationResult) {
        if (orchestrationResult == null)
            throw new InvalidParameterException("orchestrationResult cannot be null!");
        return orchestrationResult.getAuthorizationTokens() == null
                ? null
                : orchestrationResult.getAuthorizationTokens().get("HTTP-INSECURE-JSON");
    }

    public <T> T consumeServiceHTTP(final Class<T> responseType, final HttpMethod httpMethod, final String address,
            final int port,
            final String serviceUri, final String interfaceName, final String token, final Object payload,
            final String... queryParams) {
        return arrowheadService.consumeServiceHTTP(responseType, httpMethod, address,
                port, serviceUri, interfaceName,
                token, payload, queryParams);
    }

    protected String getUriScheme() {
        return sslEnabled ? "https" : "http";
    }

    public ArrowheadSystemInformation getSystemInformationByName(String systemName) {
        if (!StringUtilities.isValid(systemName))
            throw new InvalidParameterException("systemName is not valid!");
        Optional<ArrowheadSystemInformation> system = connectedSystemsLocalRepository
                .stream()
                .filter(s -> s.getSystemName().equals(systemName))
                .findFirst();
        if (system.isEmpty())
            throw new InvalidParameterException("There is no any system called: " + systemName);
        return system.get();
    }

    public boolean checkSystemIsExistByName(String systemName) {
        if (!StringUtilities.isValid(systemName))
            throw new InvalidParameterException("systemName is not valid!");
        return connectedSystemsLocalRepository
                .stream()
                .anyMatch(system -> system.getSystemName().equals(systemName));
    }
}
