package nl.tue.systemconnectorpackage.common.implementations;

import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;
import nl.tue.systemconnectorpackage.common.models.MultipartFormDataDescription;
import nl.tue.systemconnectorpackage.common.HttpUtilityService;
import nl.tue.systemconnectorpackage.common.StringUtilities;
import nl.tue.systemconnectorpackage.common.ValidationUtilities;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Default implementation of HttpUtilityService
 */
public class HttpUtilityServiceDefaultImp implements HttpUtilityService {
    @Override
    public <T> T sendHttpPostRequestWithMultipartFormData(String uri,
            List<MultipartFormDataDescription> multipartFormDataDescriptions,
            Class<T> responseType)
            throws IOException {
        return sendHttpPostRequestWithMultipartFormData(uri, new HashMap<>(),
                multipartFormDataDescriptions, responseType);
    }

    @Override
    public <T> T sendHttpPostRequestWithMultipartFormData(String uri,
            Map<String, String> queryStringKeyValues,
            List<MultipartFormDataDescription> multipartFormDataDescriptions,
            Class<T> responseType)
            throws IOException {
        return sendHttpRequestWithMultipartFormData(HttpMethod.POST, uri, queryStringKeyValues,
                multipartFormDataDescriptions, responseType);
    }

    private <T> T sendHttpRequestWithMultipartFormData(HttpMethod method,
            String uri,
            Map<String, String> queryStringKeyValues,
            List<MultipartFormDataDescription> multipartFormDataDescriptions,
            Class<T> responseType)
            throws IOException {
        validateHttpRequestSendingParameters(uri, multipartFormDataDescriptions, responseType);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        for (MultipartFormDataDescription multipartFormDataDescription : multipartFormDataDescriptions) {
            validateMultipartFormDataDescription(multipartFormDataDescription);

            File fileToSend = File.createTempFile(multipartFormDataDescription.getFileName(),
                    multipartFormDataDescription.getFileExtension());
            FileUtils.writeByteArrayToFile(fileToSend, multipartFormDataDescription.getFileContent().getBytes());
            body.add(multipartFormDataDescription.getFileName(), new FileSystemResource(fileToSend));
        }

        // TO-DO: RestTemplate is directly initiating, it should be injected!
        ResponseEntity<T> response = (new RestTemplate()).exchange(buildUriWithQueryString(uri, queryStringKeyValues),
                method,
                new HttpEntity<>(body), responseType);
        return response.getBody();
    }

    private static <T> void validateHttpRequestSendingParameters(final String uri,
            final List<MultipartFormDataDescription> multipartFormDataDescriptions, final Class<T> responseType) {
        if (ValidationUtilities.containsNull(responseType, uri, multipartFormDataDescriptions))
            throw new InvalidParameterException("HttpRequest sending parameters are not valid!");
    }

    private static void validateMultipartFormDataDescription(
            MultipartFormDataDescription multipartFormDataDescription) {
        if (multipartFormDataDescription == null)
            throw new InvalidParameterException("multipartFormDataDescription is not valid!");
        if (!StringUtilities.isValid(multipartFormDataDescription.getFileName()))
            throw new InvalidParameterException("fileName is not valid!");
        if (!StringUtilities.isValid(multipartFormDataDescription.getFileContent()))
            throw new InvalidParameterException("fileContent is not valid!");
    }

    private static String buildUriWithQueryString(final String uri, final Map<String, String> queryParams) {
        StringBuilder queryStringPart = new StringBuilder(uri + "?");
        for (var queryParam : queryParams.entrySet()) {
            queryStringPart.append(queryParam.getKey());
            queryStringPart.append("=");
            queryStringPart.append(queryParam.getValue());
            queryStringPart.append("&");
        }
        queryStringPart.deleteCharAt(queryStringPart.length() - 1);
        return queryStringPart.toString();
    }

    @Override
    public <T> T sendHttpPutRequestWithMultipartFormData(String uri,
            Map<String, String> queryStringKeyValues,
            List<MultipartFormDataDescription> multipartFormDataDescriptions,
            Class<T> responseType)
            throws IOException {
        return sendHttpRequestWithMultipartFormData(HttpMethod.PUT, uri, new HashMap<>(), multipartFormDataDescriptions,
                responseType);
    }
}
