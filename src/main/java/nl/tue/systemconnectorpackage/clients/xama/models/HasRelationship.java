package nl.tue.systemconnectorpackage.clients.xama.models;

import java.util.Date;

/**
 * Represents HasRelationship model which exists in Xama system
 */
public class HasRelationship {
    private Long id;
    private Element element;
    private Element thisElement;
    private Date created;
    private String type;

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
     * @return Element return the thisElement
     */
    public Element getThisElement() {
        return thisElement;
    }

    /**
     * @param thisElement the thisElement to set
     */
    public void setThisElement(Element thisElement) {
        this.thisElement = thisElement;
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

}
