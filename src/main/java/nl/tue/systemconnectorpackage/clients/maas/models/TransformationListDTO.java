package nl.tue.systemconnectorpackage.clients.maas.models;

import java.util.List;

public class TransformationListDTO {
    private List<TransformationDTO> transformations;

    /**
     * @return List<TransformationDTO> return the transformations
     */
    public List<TransformationDTO> getTransformations() {
        return transformations;
    }

    /**
     * @param transformations the transformations to set
     */
    public void setTransformations(List<TransformationDTO> transformations) {
        this.transformations = transformations;
    }

}
