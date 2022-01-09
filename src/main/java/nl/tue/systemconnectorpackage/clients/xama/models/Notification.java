package nl.tue.systemconnectorpackage.clients.xama.models;

import java.util.Date;

/**
 * Represents Notification model which exists in Xama system
 */
public class Notification {
    private String uuid;
    private Long id;
    private Date created;
    private Boolean verified = false;
    private Sent sent;
    private To to;

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
     * @return Boolean return the verified
     */
    public Boolean isVerified() {
        return verified;
    }

    /**
     * @param verified the verified to set
     */
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    /**
     * @return Sent return the sent
     */
    public Sent getSent() {
        return sent;
    }

    /**
     * @param sent the sent to set
     */
    public void setSent(Sent sent) {
        this.sent = sent;
    }

    /**
     * @return To return the to
     */
    public To getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(To to) {
        this.to = to;
    }

}
