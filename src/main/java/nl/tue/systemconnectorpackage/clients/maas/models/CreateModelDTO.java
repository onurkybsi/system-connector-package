package nl.tue.systemconnectorpackage.clients.maas.models;

public class CreateModelDTO {
    private String name;
    private String description;
    private String path;
    private String type;
    private String repoUrl;
    private String versionNumber = "1.0.0";
    private int stars = 0;
    private int forks = 0;
    private int branches = 0;

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
     * @return String return the repoUrl
     */
    public String getRepoUrl() {
        return repoUrl;
    }

    /**
     * @param repoUrl the repoUrl to set
     */
    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    /**
     * @return String return the versionNumber
     */
    public String getVersionNumber() {
        return versionNumber;
    }

    /**
     * @param versionNumber the versionNumber to set
     */
    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    /**
     * @return int return the stars
     */
    public int getStars() {
        return stars;
    }

    /**
     * @param stars the stars to set
     */
    public void setStars(int stars) {
        this.stars = stars;
    }

    /**
     * @return int return the forks
     */
    public int getForks() {
        return forks;
    }

    /**
     * @param forks the forks to set
     */
    public void setForks(int forks) {
        this.forks = forks;
    }

    /**
     * @return int return the branches
     */
    public int getBranches() {
        return branches;
    }

    /**
     * @param branches the branches to set
     */
    public void setBranches(int branches) {
        this.branches = branches;
    }

}
