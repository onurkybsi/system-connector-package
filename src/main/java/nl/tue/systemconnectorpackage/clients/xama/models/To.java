package nl.tue.systemconnectorpackage.clients.xama.models;

/**
 * Represents To model which exists in Xama system
 */
public class To {
    private Long id;
    private Notification notification;
    private Element element;

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

}
