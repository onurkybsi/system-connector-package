package nl.tue.systemconnectorpackage.common.models;

/**
 * Describes a file to send in the Http multipart/form-data request
 */
public class MultipartFormDataDescription {
    private String fileName;
    private String fileContent;
    private String fileExtension;

    /**
     * Construct a HttpMultipartFormDataContext
     *
     * @param fileName      Name of the file
     * 
     * @param fileContent   Content of the file in string form
     * 
     * @param fileExtension Extension of file which must end with .*** like ".json"
     * 
     */
    public MultipartFormDataDescription(String fileName, String fileContent, String fileExtension) {
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.fileExtension = fileExtension;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}