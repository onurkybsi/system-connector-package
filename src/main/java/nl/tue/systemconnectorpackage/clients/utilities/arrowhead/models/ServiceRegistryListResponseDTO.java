package nl.tue.systemconnectorpackage.clients.utilities.arrowhead.models;

import java.io.Serializable;
import java.util.List;

import eu.arrowhead.common.dto.shared.ServiceRegistryResponseDTO;

public class ServiceRegistryListResponseDTO implements Serializable {
    private List<ServiceRegistryResponseDTO> data;

    /**
     * @return List<ServiceRegistryResponseDTO> return the data
     */
    public List<ServiceRegistryResponseDTO> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<ServiceRegistryResponseDTO> data) {
        this.data = data;
    }

}
