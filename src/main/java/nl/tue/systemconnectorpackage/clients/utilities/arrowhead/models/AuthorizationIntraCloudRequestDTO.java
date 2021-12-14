package nl.tue.systemconnectorpackage.clients.utilities.arrowhead.models;

import java.io.Serializable;
import java.util.List;

/**
 * Represents request value of consumer registration to Authorization core system
 */
public class AuthorizationIntraCloudRequestDTO implements Serializable {
    private static final long serialVersionUID = 1322804880331971340L;

    private Long consumerId;
    private List<Long> providerIds;
    private List<Long> serviceDefinitionIds;
    private List<Long> interfaceIds;

    public AuthorizationIntraCloudRequestDTO() {
    }

    public AuthorizationIntraCloudRequestDTO(final Long consumerId, final List<Long> providerIds, final List<Long> serviceDefinitionIds, final List<Long> interfaceIds) {
        this.consumerId = consumerId;
        this.providerIds = providerIds;
        this.serviceDefinitionIds = serviceDefinitionIds;
        this.interfaceIds = interfaceIds;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public List<Long> getProviderIds() {
        return providerIds;
    }

    public List<Long> getServiceDefinitionIds() {
        return serviceDefinitionIds;
    }

    public List<Long> getInterfaceIds() {
        return interfaceIds;
    }

    public void setConsumerId(final Long consumerId) {
        this.consumerId = consumerId;
    }

    public void setProviderIds(final List<Long> providerIds) {
        this.providerIds = providerIds;
    }

    public void setServiceDefinitionIds(final List<Long> serviceDefinitionIds) {
        this.serviceDefinitionIds = serviceDefinitionIds;
    }

    public void setInterfaceIds(final List<Long> interfaceIds) {
        this.interfaceIds = interfaceIds;
    }
}