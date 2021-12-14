package nl.tue.systemconnectorpackage.clients.maas;

import nl.tue.systemconnectorpackage.clients.maas.models.TransformationListDTO;

/**
 * Client module for model-transformer which is subsystem of MAAS
 */
public interface ModelTransformerClient {
    /**
     * Passes the given parameters to the "model-transformer" service of the
     * model-transformer sub-system of MAAS
     *
     * @param name
     * @param format
     * @param elems
     * @param repo
     * @return Response from MAAS system
     */
    TransformationListDTO transformModel(String format);
}
