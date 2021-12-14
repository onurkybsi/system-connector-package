package nl.tue.systemconnectorpackage.clients.maas.models;

public class RepositoryMetadata {
    private String url = "";
    private int stars = 0;
    private int forks = 0;
    private int branches = 0;

    /**
     * @return String return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
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
