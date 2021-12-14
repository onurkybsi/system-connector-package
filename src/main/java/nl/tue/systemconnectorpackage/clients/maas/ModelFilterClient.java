package nl.tue.systemconnectorpackage.clients.maas;

import java.util.HashMap;

/**
 * Client module for model-filterer which is subsystem of MAAS
 */
public interface ModelFilterClient {
    /**
     * Passes the given parameters to the "model-filter" service of the
     * model-filterer sub-system of MAAS
     *
     * @param name
     * @param format
     * @param elems
     * @param repo
     * @return Response from MAAS system
     */
    HashMap<String, Object> filterModel(String name, String format, String elems, String repo);
}
