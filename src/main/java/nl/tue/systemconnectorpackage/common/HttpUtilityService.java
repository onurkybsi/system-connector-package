package nl.tue.systemconnectorpackage.common;

import nl.tue.systemconnectorpackage.common.models.MultipartFormDataDescription;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Provides various functionalities for Http protocol based operations
 */
public interface HttpUtilityService {
        /**
         * Sends a HTTP POST request with multipart/form-data content type
         *
         * @param uri                           Uri of the server
         * @param multipartFormDataDescriptions List of the multipart form data
         *                                      descriptions will
         *                                      send
         * @param responseType                  Expected response type from the remote
         *                                      server
         * @param <T>                           Expected response type
         * @return Response from the server
         * @throws IOException
         */
        <T> T sendHttpPostRequestWithMultipartFormData(String uri,
                        List<MultipartFormDataDescription> multipartFormDataDescriptions,
                        Class<T> responseType)
                        throws IOException;

        /**
         * Sends a HTTP POST request with multipart/form-data content type
         *
         * @param uri                           Uri of the server
         * @param queryStringKeyValues          Query string key values map of HTTP
         *                                      request
         * @param multipartFormDataDescriptions List of the multipart form data
         *                                      descriptions will
         *                                      send
         * @param responseType                  Expected response type from the remote
         *                                      server
         * @param <T>                           Expected response type
         * @return Response from the server
         * @throws IOException
         */
        <T> T sendHttpPostRequestWithMultipartFormData(String uri,
                        Map<String, String> queryStringKeyValues,
                        List<MultipartFormDataDescription> multipartFormDataDescriptions,
                        Class<T> responseType)
                        throws IOException;

        /**
         * Sends a HTTP PUT request with multipart/form-data content type
         *
         * @param uri                           Uri of the server
         * @param queryStringKeyValues          Query string key values map of HTTP
         *                                      request
         * @param multipartFormDataDescriptions List of the multipart form data
         *                                      descriptions will
         *                                      send
         * @param responseType                  Expected response type from the remote
         *                                      server
         * @param <T>                           Expected response type
         * @return Response from the server
         * @throws IOException
         */
        <T> T sendHttpPutRequestWithMultipartFormData(String uri,
                        Map<String, String> queryStringKeyValues,
                        List<MultipartFormDataDescription> multipartFormDataDescriptions,
                        Class<T> responseType)
                        throws IOException;
}
