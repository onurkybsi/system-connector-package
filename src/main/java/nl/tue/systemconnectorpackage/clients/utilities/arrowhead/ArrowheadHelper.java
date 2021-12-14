package nl.tue.systemconnectorpackage.clients.utilities.arrowhead;

import eu.arrowhead.common.dto.shared.OrchestrationResponseDTO;
import eu.arrowhead.common.dto.shared.OrchestrationResultDTO;
import eu.arrowhead.common.exception.UnavailableServerException;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.exceptions.ArrowheadOrchestrationException;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;

import org.springframework.http.HttpMethod;

/**
 * Provides various functionalities to communicate with Arrowhead
 */
public interface ArrowheadHelper {
        /**
         * Loads JSON file by the given resource path and registers the systems to
         * Arrowhead by the information in the file
         *
         * @param systemDefinitionListResourcePath JSON file resource path for system
         *                                         definitions
         * @see "/resources/system-definitions/example-system-definitions.json"
         */
        void registerSystemsToArrowhead(String systemDefinitionListResourcePath)
                        throws UnavailableServerException, IOException, JsonSyntaxException;

        /**
         * Proceeds orchestration of service consuming
         * NOTE: If we use MATCHMAKING flag we should return OrchestrationResultDTO
         *
         * @param serviceName Consuming service name
         * @return Orchestration response
         */
        OrchestrationResponseDTO proceedOrchestration(String serviceName);

        /**
         * Validates orchestration response for use
         * 
         * @param orchestrationResponse Orchestration response to use
         * @throws InvalidParameterException If one of response values is not valid
         */
        void validateOrchestrationResponse(OrchestrationResponseDTO orchestrationResponse)
                        throws ArrowheadOrchestrationException;

        /**
         * Consumes HTTP service from producer
         * 
         * @param <T>                 Response type
         * @param serviceName         Consuming service name
         * @param orchestrationResult Orchestration result of consuming service
         * @param responseType
         * @param payload             Payload of HTTP request
         * @param queryParams         Query parameters of HTTP request
         * @return
         */
        <T> T consumeServiceHTTPByOrchestrationResult(OrchestrationResultDTO orchestrationResult,
                        Class<T> responseType, Object payload, String... queryParams);

        /**
         * Extract access token from given orchestration result
         * 
         * @param orchestrationResult Orchestration result
         * @return Access token
         */
        String extractAuthorizationTokenFromOrchestrationResult(final OrchestrationResultDTO orchestrationResult);

        /**
         * Proxy method to ArrowheadService.consumeServiceHTTP
         *
         * @param <T>
         * @param responseType  Response type
         * @param httpMethod    HTTP verb of the request
         * @param address       Target server address
         * @param port          Targer server port
         * @param serviceUri    Target server URI
         * @param interfaceName Interface of consuming service
         * @param token         Access token of target
         * @param payload       Payload of HTTP request
         * @param queryParams   Query parameters of HTTP request
         * @return Service response
         */
        <T> T consumeServiceHTTP(final Class<T> responseType, final HttpMethod httpMethod, final String address,
                        final int port,
                        final String serviceUri, final String interfaceName, final String token, final Object payload,
                        final String... queryParams);

        /**
         * Receives interface name for the service
         *
         * @return Interface name
         */
        String getInterfaceName();
}