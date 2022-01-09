package nl.tue.systemconnectorpackage.clients.xama.models;

import java.util.Date;
import java.util.List;

/**
 * Represents Element model which exists in Xama system
 */
public class Element {
    private String uuid;
    private Long id;
    private String name;
    private String localization;
    private Date created;
    private Date modified;
    private Date verified;
    private List<HasRelationship> hasRelationships;
    private List<CurrentRelationship> currentRelationships;
    private Contains contains;
    private Contained contained;
    private List<To> notifications;

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
     * @return String return the localization
     */
    public String getLocalization() {
        return localization;
    }

    /**
     * @param localization the localization to set
     */
    public void setLocalization(String localization) {
        this.localization = localization;
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
     * @return Date return the modified
     */
    public Date getModified() {
        return modified;
    }

    /**
     * @param modified the modified to set
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }

    /**
     * @return Date return the verified
     */
    public Date getVerified() {
        return verified;
    }

    /**
     * @param verified the verified to set
     */
    public void setVerified(Date verified) {
        this.verified = verified;
    }

    /**
     * @return List<HasRelationship> return the hasRelationships
     */
    public List<HasRelationship> getHasRelationships() {
        return hasRelationships;
    }

    /**
     * @param hasRelationships the hasRelationships to set
     */
    public void setHasRelationships(List<HasRelationship> hasRelationships) {
        this.hasRelationships = hasRelationships;
    }

    /**
     * @return List<CurrentRelationship> return the currentRelationships
     */
    public List<CurrentRelationship> getCurrentRelationships() {
        return currentRelationships;
    }

    /**
     * @param currentRelationships the currentRelationships to set
     */
    public void setCurrentRelationships(List<CurrentRelationship> currentRelationships) {
        this.currentRelationships = currentRelationships;
    }

    /**
     * @return Contains return the contains
     */
    public Contains getContains() {
        return contains;
    }

    /**
     * @param contains the contains to set
     */
    public void setContains(Contains contains) {
        this.contains = contains;
    }

    /**
     * @return Contained return the contained
     */
    public Contained getContained() {
        return contained;
    }

    /**
     * @param contained the contained to set
     */
    public void setContained(Contained contained) {
        this.contained = contained;
    }

    /**
     * @return List<To> return the notifications
     */
    public List<To> getNotifications() {
        return notifications;
    }

    /**
     * @param notifications the notifications to set
     */
    public void setNotifications(List<To> notifications) {
        this.notifications = notifications;
    }

}
