package nl.tue.systemconnectorpackage.clients.utilities.arrowhead.implementations;

import eu.arrowhead.common.CommonConstants;

/**
 * Contains constants values for Arrowhead services
 */
public class ArrowheadConstants {
    /**
     * Service interface value for secure HTTP service
     */
    public static final String SECURE_HTTP_SERVICE_INTERFACE = "HTTP-SECURE-JSON";
    /**
     * Service interface value for insecure HTTP service
     */
    public static final String INSECURE_HTTP_SERVICE_INTERFACE = "HTTP-INSECURE-JSON";
    /**
     * ServiceRegistry endpoint value for system registration to Arrowhead
     */
    public static final String CONSUMER_SYSTEM_REGISTRATION_URI = "serviceregistry/register-system";
    /**
     * ServiceRegistry endpoint value for registration of consuming a service to Arrowhead
     */
    public static final String CONSUMER_REGISTRATION_URI = CommonConstants.AUTHORIZATION_URI + "/mgmt/intracloud";
}