package nl.tue.systemconnectorpackage.clients.maas.models;

import java.util.List;

public class ModelElementMetadata {
    private int edgeCount;
    private int taskCount;
    private int eventCount;
    private int ANDJoinCount;
    private int ANDSplitCount;
    private int ORJoinCount;
    private int ORSplitCount;
    private int XORJoinCount;
    private int XORSplitCount;
    private List<String> nodeNames;

    /**
     * @return int return the edgeCount
     */
    public int getEdgeCount() {
        return edgeCount;
    }

    /**
     * @param edgeCount the edgeCount to set
     */
    public void setEdgeCount(int edgeCount) {
        this.edgeCount = edgeCount;
    }

    /**
     * @return int return the taskCount
     */
    public int getTaskCount() {
        return taskCount;
    }

    /**
     * @param taskCount the taskCount to set
     */
    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    /**
     * @return int return the eventCount
     */
    public int getEventCount() {
        return eventCount;
    }

    /**
     * @param eventCount the eventCount to set
     */
    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    /**
     * @return int return the ANDJoinCount
     */
    public int getANDJoinCount() {
        return ANDJoinCount;
    }

    /**
     * @param ANDJoinCount the ANDJoinCount to set
     */
    public void setANDJoinCount(int ANDJoinCount) {
        this.ANDJoinCount = ANDJoinCount;
    }

    /**
     * @return int return the ANDSplitCount
     */
    public int getANDSplitCount() {
        return ANDSplitCount;
    }

    /**
     * @param ANDSplitCount the ANDSplitCount to set
     */
    public void setANDSplitCount(int ANDSplitCount) {
        this.ANDSplitCount = ANDSplitCount;
    }

    /**
     * @return int return the ORJoinCount
     */
    public int getORJoinCount() {
        return ORJoinCount;
    }

    /**
     * @param ORJoinCount the ORJoinCount to set
     */
    public void setORJoinCount(int ORJoinCount) {
        this.ORJoinCount = ORJoinCount;
    }

    /**
     * @return int return the ORSplitCount
     */
    public int getORSplitCount() {
        return ORSplitCount;
    }

    /**
     * @param ORSplitCount the ORSplitCount to set
     */
    public void setORSplitCount(int ORSplitCount) {
        this.ORSplitCount = ORSplitCount;
    }

    /**
     * @return int return the XORJoinCount
     */
    public int getXORJoinCount() {
        return XORJoinCount;
    }

    /**
     * @param XORJoinCount the XORJoinCount to set
     */
    public void setXORJoinCount(int XORJoinCount) {
        this.XORJoinCount = XORJoinCount;
    }

    /**
     * @return int return the XORSplitCount
     */
    public int getXORSplitCount() {
        return XORSplitCount;
    }

    /**
     * @param XORSplitCount the XORSplitCount to set
     */
    public void setXORSplitCount(int XORSplitCount) {
        this.XORSplitCount = XORSplitCount;
    }

    /**
     * @return List<String> return the nodeNames
     */
    public List<String> getNodeNames() {
        return nodeNames;
    }

    /**
     * @param nodeNames the nodeNames to set
     */
    public void setNodeNames(List<String> nodeNames) {
        this.nodeNames = nodeNames;
    }
}