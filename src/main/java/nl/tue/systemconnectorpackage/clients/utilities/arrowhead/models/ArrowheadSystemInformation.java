package nl.tue.systemconnectorpackage.clients.utilities.arrowhead.models;

import java.util.List;

/**
 * Represents meta information for the systems which are connected to Arrowhead
 * by the system-connector
 */
public class ArrowheadSystemInformation {
    private long id;
    private String systemName;
    private String address;
    private int port;
    private List<ArrowheadServiceInformation> producedServices;
    private List<String> consumedServicesNames;

    public ArrowheadSystemInformation(String systemName, String address, int port,
            List<ArrowheadServiceInformation> producedServices,
            List<String> consumedServiceNames) {
        this.systemName = systemName;
        this.address = address;
        this.port = port;
        this.producedServices = producedServices;
        this.consumedServicesNames = consumedServiceNames;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public List<ArrowheadServiceInformation> getProducedServices() {
        return producedServices;
    }

    public List<String> getConsumedServiceNames() {
        return consumedServicesNames;
    }
}