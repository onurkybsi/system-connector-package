package nl.tue.systemconnectorpackage.clients.utilities.arrowhead.models;

import java.util.Map;

/**
 * Represents meta information for the service which are provided by the
 * provider systems
 */
public class ArrowheadServiceInformation {
    private Long id;
    private Long providerId;
    private String name;
    private String uri;
    private Map<String, String> metadata;

    public ArrowheadServiceInformation(Long id, Long providerId, String name, String uri,
            Map<String, String> metadata) {
        this.id = id;
        this.providerId = providerId;
        this.name = name;
        this.uri = uri;
        this.metadata = metadata;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }
}