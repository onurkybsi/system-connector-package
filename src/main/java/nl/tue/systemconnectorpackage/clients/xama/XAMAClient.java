package nl.tue.systemconnectorpackage.clients.xama;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import nl.tue.systemconnectorpackage.clients.xama.models.Match;
import nl.tue.systemconnectorpackage.clients.xama.models.Model;

/**
 * Represents client module for XAMA system
 */
public interface XAMAClient {
    /**
     * Passes the given parameters to the "find-possible-relations" service of the
     * Xama system
     * 
     * @param file
     * @param locationRepository
     * @exception IOException
     * @return
     */
    ArrayList<Match> findPossibleRelationts(MultipartFile file, String locationRepository) throws IOException;

    /**
     * Passes the given parameters to the "create-new-model-xama" service of the
     * Xama system
     * 
     * @param name
     * @param type
     * @param fileURL
     * @param isLocal
     * @return
     */
    Model createNewModel(String name, String type, String fileURL, boolean isLocal);
}
