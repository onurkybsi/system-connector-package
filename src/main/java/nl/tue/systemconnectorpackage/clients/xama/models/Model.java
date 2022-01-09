package nl.tue.systemconnectorpackage.clients.xama.models;

import java.util.Date;
import java.util.List;

/**
 * Represents Model model which exists in Xama system
 */
public class Model {
    private String uuid;
    private Long id;
    private String name;
    private String type;
    private Date created;
    private String checksumHash;
    private String fileURL;
    private boolean isLocal;
    private boolean updated;
    private List<Contains> contains;
    private List<Contained> contained;

    /**
     * @return String return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return Date return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return String return the checksumHash
     */
    public String getChecksumHash() {
        return checksumHash;
    }

    /**
     * @param checksumHash the checksumHash to set
     */
    public void setChecksumHash(String checksumHash) {
        this.checksumHash = checksumHash;
    }

    /**
     * @return String return the fileURL
     */
    public String getFileURL() {
        return fileURL;
    }

    /**
     * @param fileURL the fileURL to set
     */
    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    /**
     * @return boolean return the isLocal
     */
    public boolean isIsLocal() {
        return isLocal;
    }

    /**
     * @param isLocal the isLocal to set
     */
    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    /**
     * @return boolean return the updated
     */
    public boolean isUpdated() {
        return updated;
    }

    /**
     * @param updated the updated to set
     */
    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    /**
     * @return List<Contains> return the contains
     */
    public List<Contains> getContains() {
        return contains;
    }

    /**
     * @param contains the contains to set
     */
    public void setContains(List<Contains> contains) {
        this.contains = contains;
    }

    /**
     * @return List<Contained> return the contained
     */
    public List<Contained> getContained() {
        return contained;
    }

    /**
     * @param contained the contained to set
     */
    public void setContained(List<Contained> contained) {
        this.contained = contained;
    }

}
