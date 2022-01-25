## Clients Package

It includes clients that provide services so that the services of the underlying system can be consumed. Clients were developed by developing subpackages for each of the systems which will be consumed.

Subpackages:
* _maas_: It contains various modules related to a client that includes proxy services to the services of the MAAS system. [_more info..._](https://github.com/onurkybsi/system-connector-package/blob/master/src/main/java/nl/tue/systemconnectorpackage/clients/maas)
* _samos_: It contains various modules related to a client that includes proxy services to the services of the SAMOS system. [_more info..._](https://github.com/onurkybsi/system-connector-package/blob/master/src/main/java/nl/tue/systemconnectorpackage/clients/samos)
* _xama_: It contains various modules related to a client that includes proxy services to the services of the XAMA system. [_more info..._](https://github.com/onurkybsi/system-connector-package/blob/master/src/main/java/nl/tue/systemconnectorpackage/clients/xama)
* _utilities_: Contains utilities that can be used by modules of client packages:
    * `ArrowheadHelper`: It provides various services for the Arrowhead framework to use its services more effectively. [_more info..._](https://github.com/onurkybsi/system-connector-package/blob/master/src/main/java/nl/tue/systemconnectorpackage/clients/utilities/arrowhead/ArrowheadHelper.java)

### Design of A Client Package
The most important consideration when developing clients was that the client was independent of the underlying communication type. In _version 1.0.0_, _system-connector-package_ provides inter-system communication using the [Arrowhead framework](https://github.com/eclipse-arrowhead/core-java-spring). However, considering that this situation may be changed in later versions, an abstraction has been desired to be provided between the users of _system-connector-package_ and the clients.

#### Package Structure
* **_xClient_**: The parent package that contains the client of system X and other modules that the client module uses. 
    * **_XClientInterface.java_**: Java interface containing consumable services of system X.
    * **_models_**: A subpackage containing models representing the input and output data of the services of system X.
        * XServiceResponse.java
        * XServiceRequest.java
    * **_implementations_**: Subpackage with various _xClientInterface_ implementations according to communication types.
        * XClientArrowheadImp.java
        * XClientKafkaImp.java
        * XClientRESTImp.java

### Arrowhead Service Consuming Implementation

_Version 1.0.0_ has only _Arrowhead framework_ implementation for inter-system communication. The implementation of a proxy service that consumes a service of a provider registered on the _Arrowhead framework_ can be exemplified by the _create-new-model-xama_ service of the _XAMA_ client as follows.

``` java
    public Model createNewModel(String name, String type, String fileURL, boolean isLocal) {
        // Orchestration process is applied:
        OrchestrationResponseDTO orchestrationResponse = arrowheadHelper
                .proceedOrchestration("create-new-model-xama");
        
        // Orchestration response is validated and if the response is not valid ArrowheadOrchestrationException will be thrown:
        arrowheadHelper.validateOrchestrationResponse(orchestrationResponse);

        // More than one provider can be reached as a result of the orchestration.
        // The first provider is the most suitable for the service, so the information of the first provider is taken as follows.
        final OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);

        // create-new-model-xama service is a RESTful service and needs various parameters.
        // These parameters are obtained from the orchestration result as follows.
        final String[] queryParams = { orchestrationResult.getMetadata().get("request-param-name"), name,
                orchestrationResult.getMetadata().get("request-param-type"), type,
                orchestrationResult.getMetadata().get("request-param-fileURL"), fileURL,
                orchestrationResult.getMetadata().get("request-param-isLocal"), isLocal ? "true" : "false"
        };

        // Finally the RESTful service can be consumed as follows.
        return arrowheadHelper.consumeServiceHTTPByOrchestrationResult(orchestrationResult,
                Model.class, null, queryParams);
    }
```

### ArrowheadHelper.java services

* `void registerSystemsToArrowhead(String systemDefinitionListResourcePath)
                        throws UnavailableServerException, IOException, JsonSyntaxException`:
    * _Version 1.0.0_ supports only Arrowhead framework for inter-system communication. For this reason, _system-connector-package_ users must be registered in _Arrowhead_. Although this registration can be done manually by users, _system-connector-package_ provides the `registerSystemsToArrowhead` service for this registration. [As in the example](https://github.com/onurkybsi/system-connector-package/blob/master/doc/example-config-files/example-system-definition.json), all the details of the system to be recorded must be specified in a JSON. Then the path of this JSON file is given as a parameter to the `registerSystemsToArrowhead` service and the service completes all necessary registration.
