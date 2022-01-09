package nl.tue.systemconnectorpackage.clients.xama.models;

import java.util.ArrayList;

/**
 * Represents Match model which exists in Xama system
 */
public class Match {
    private ArrayList<String> nameOCRList;
    private String match;
    private String auxMatchNames;
    private ArrayList<String> locationList;
    private String subSystem;
    private String checkSumHashModelRepository;
    private String checkSumHashModelOCR;
    private String modelRepositoryLocation;
    private String modelRepositoryName;

    public String getAuxMatchNames() {
        if (this.auxMatchNames == null) {
            return this.getMatch();
        }

        return auxMatchNames;
    }

    public void setAuxMatchNames(String auxMatchNames) {
        this.auxMatchNames = auxMatchNames;
    }

    public String getModelRepositoryName() {
        return modelRepositoryName;
    }

    public void setModelRepositoryName(String modelRepositoryName) {
        this.modelRepositoryName = modelRepositoryName;
    }

    public ArrayList<String> getNameOCRList() {
        return nameOCRList;
    }

    public void setNameOCRList(ArrayList<String> nameOCR) {
        this.nameOCRList = nameOCR;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public ArrayList<String> getLocationList() {
        return locationList;
    }

    public void setLocationList(ArrayList<String> location) {
        this.locationList = location;
    }

    public String getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

    public String getCheckSumHashModelRepository() {
        return checkSumHashModelRepository;
    }

    public void setCheckSumHashModelRepository(String checkSumHashModelRepository) {
        this.checkSumHashModelRepository = checkSumHashModelRepository;
    }

    public String getCheckSumHashModelOCR() {
        return checkSumHashModelOCR;
    }

    public void setCheckSumHashModelOCR(String checkSumHashModelOCR) {
        this.checkSumHashModelOCR = checkSumHashModelOCR;
    }

    public void addNameOCRLocation(String nameOCR, String location) {
        if (!nameOCRList.contains(nameOCR)) {
            nameOCRList.add(nameOCR);
        }
        if (!locationList.contains(location)) {
            locationList.add(location);
        }
    }

    public String getModelRepositoryLocation() {
        return modelRepositoryLocation;
    }

    public void setModelRepositoryLocation(String modelRepositoryLocation) {
        this.modelRepositoryLocation = modelRepositoryLocation;
    }

    public String toString() {
        return "OCR Names:" + nameOCRList + ", Match: " + getAuxMatchNames() + ", Location:" + locationList
                + ", SubSystem: " + getSubSystem();
    }
}
