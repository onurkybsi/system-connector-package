package nl.tue.systemconnectorpackage.clients.maas;

import nl.tue.systemconnectorpackage.clients.maas.models.CreateModelDTO;
import nl.tue.systemconnectorpackage.clients.maas.models.Domain;
import nl.tue.systemconnectorpackage.clients.maas.models.ModelDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

/**
 * Client module for repository-manager which is subsystem of MAAS
 */
public interface RepositoryManagerClient {
        /**
         * Passes the given parameters to the "get-domains" service of the
         * repository-manager system
         *
         * @param name
         * @param offset
         * @param limit
         * @return Response from MAAS system
         */
        HashMap<String, Object> getDomains(String name, int offset, int limit);

        /**
         * Passes the given parameters to the "create-domains" service of the
         * repository-manager system
         *
         * @param domainData
         * @param file
         * @return Response from MAAS system
         * @throws IOException
         */
        HashMap<String, Object> createDomain(Domain domainData, MultipartFile file)
                        throws IOException;

        /**
         * Passes the given parameters to the "update-domain" service of the
         * repository-manager system
         *
         * @param newDomain Updated domain
         * @param id        Domain id to update
         * @return Response from MAAS system
         */
        Domain updateDomain(Domain newDomain, final String id);

        /**
         * Passes the given parameters to the "delete-domain" service of the
         * repository-manager system
         *
         * @param id Domain id to delete
         * @return Response from MAAS system
         */
        void deleteDomain(final String id);

        /**
         * Passes the given parameters to the "get-models" service of the
         * repository-manager system
         *
         * @param name
         * @param offset
         * @param limit
         * @return Response from MAAS system
         * @throws Exception
         */
        HashMap<String, Object> getModels(String name, int offset, int limit);

        /**
         * Passes the given parameters to the "create-model" service of the
         * repository-manager system
         *
         * @param modelDTO
         * @param file
         * @return Response from MAAS system
         * @throws IOException
         */
        HashMap<String, Object> createModel(CreateModelDTO modelDTO, MultipartFile file)
                        throws IOException;

        /**
         * Passes the given parameters to the "update-model" service of the
         * repository-manager system
         *
         * @param modelDTO Updated model
         * @param id       model id to update
         * @return Response from MAAS system
         */
        ModelDTO updateModel(CreateModelDTO modelDTO, MultipartFile file, final String id) throws IOException;

        /**
         * Passes the given parameters to the "delete-model" service of the
         * repository-manager system
         *
         * @param id Model id to delete
         * @return Response from MAAS system
         */
        void deleteModel(final String id);
}
