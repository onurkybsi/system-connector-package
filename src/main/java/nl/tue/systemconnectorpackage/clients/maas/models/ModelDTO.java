package nl.tue.systemconnectorpackage.clients.maas.models;

import java.util.Map;

public class ModelDTO {
    private String id;
    private String name;
    private String description;
    private String modelingLanguage;
    private String path;
    private String fileName;
    private String model;
    private String cModel;
    private ModelElementMetadata elements;
    private RepositoryMetadata repository;
    Map<String, Double> domains;
    private Long createdAt;
    private Long updatedAt;

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
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
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return String return the modelingLanguage
     */
    public String getModelingLanguage() {
        return modelingLanguage;
    }

    /**
     * @param modelingLanguage the modelingLanguage to set
     */
    public void setModelingLanguage(String modelingLanguage) {
        this.modelingLanguage = modelingLanguage;
    }

    /**
     * @return String return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return String return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return String return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return String return the cModel
     */
    public String getCModel() {
        return cModel;
    }

    /**
     * @param cModel the cModel to set
     */
    public void setCModel(String cModel) {
        this.cModel = cModel;
    }

    /**
     * @return ModelElementMetadata return the elements
     */
    public ModelElementMetadata getElements() {
        return elements;
    }

    /**
     * @param elements the elements to set
     */
    public void setElements(ModelElementMetadata elements) {
        this.elements = elements;
    }

    /**
     * @return RepositoryMetadata return the repository
     */
    public RepositoryMetadata getRepository() {
        return repository;
    }

    /**
     * @param repository the repository to set
     */
    public void setRepository(RepositoryMetadata repository) {
        this.repository = repository;
    }

    /**
     * @return Long return the createdAt
     */
    public Long getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return Long return the updatedAt
     */
    public Long getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

}
