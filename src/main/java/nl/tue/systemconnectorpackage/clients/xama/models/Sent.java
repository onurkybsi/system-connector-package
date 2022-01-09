package nl.tue.systemconnectorpackage.clients.xama.models;

/**
 * Represents Sent model which exists in Xama system
 */
public class Sent {
    private Long id;
    private Element element;
    private Notification notification;

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
     * @return Element return the element
     */
    public Element getElement() {
        return element;
    }

    /**
     * @param element the element to set
     */
    public void setElement(Element element) {
        this.element = element;
    }

    /**
     * @return Notification return the notification
     */
    public Notification getNotification() {
        return notification;
    }

    /**
     * @param notification the notification to set
     */
    public void setNotification(Notification notification) {
        this.notification = notification;
    }

}
