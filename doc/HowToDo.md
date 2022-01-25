## How To Do ?

Below are various instructions on how to extend the package in various cases.

### How to create a new client ?

A new client that system-connector-package will support can be added by following the steps below.

1. New subpackage under the `nl.tue.systemconnectorpackage.clients` is created as follows;

#### Package structure
```
+-- clients
|   +-- newClient
|       +-- NewClient.java
|       +-- models
|           +-- AnyServiceRequest.java
|           +-- AnyServiceResponse.java
|       +-- implementations
|           +-- NewClientDefaultImp.java
```

#### Contents of files
`NewClient.java`:
``` java
/**
 * Represents a client module for New system
 */
public interface NewClient {
    /**
     * Passes the given request to the "any-service" service of the
     * New system
     * 
     * @param request
     * @return
     */
    AnyServiceResponse anyService(AnyServiceRequest request);
}
```
As an concrete example; [ModelCrawlerClient.java](https://github.com/onurkybsi/system-connector-package/blob/master/src/main/java/nl/tue/systemconnectorpackage/clients/maas/ModelCrawlerClient.java)

`AnyServiceRequest.java`:

``` java
/**
 * Represents request model of anyService
 */
public class AnyServiceRequest {
    private String anyValue;

    public AnyServiceRequest(String anyValue) {
        this.anyValue = anyValue;
    }
}
```
As an concrete example; [CrawlerOptionsDTO.java](https://github.com/onurkybsi/system-connector-package/blob/master/src/main/java/nl/tue/systemconnectorpackage/clients/maas/models/CrawlerOptionsDTO.java)

`AnyServiceResponse.java`:

``` java
/**
 * Represents response model of anyService
 */
public class AnyServiceResponse {
    public boolean isSuccessful;
}
```
As an concrete example; `void`

`NewClientDefaultImp.java` :

``` java
/**
 * Default implementation of NewClient
 */
public class NewClientDefaultImp implements NewClient {
    private ArrowheadHelper arrowheadHelper;

    public ArrowheadHelper arrowheadHelper(ArrowheadHelper arrowheadHelper) {
        this.arrowheadHelper = arrowheadHelper;
    }

    @Override
    public AnyServiceResponse anyService(AnyServiceRequest request) {
        OrchestrationResponseDTO orchestrationResponse = arrowheadHelper
                .proceedOrchestration("any-service");
        
        arrowheadHelper.validateOrchestrationResponse(orchestrationResponse);

        final OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);

        // This part may vary depending on how the provider exports its service.
        // In the example here, the provider provides a RESTful API and expects a JSON request body.
        // Providers may expect files or other types of data. Accordingly, this step may change.
        // Consumption examples for different types of requests can be examined within the project.
        return arrowheadHelper.consumeServiceHTTPByOrchestrationResult(orchestrationResult,
                AnyServiceResponse.class, 
                new AnyServiceRequest(orchestrationResult.getMetadata().get("request-param-anyValue")), null);
    }
}
```
As an concrete example; [ModelCrawlerClientDefaultImp.java](https://github.com/onurkybsi/system-connector-package/blob/master/src/main/java/nl/tue/systemconnectorpackage/clients/maas/implementations/ModelCrawlerClientDefaultImp.java)

2. The created client should add the implementation to the Spring IoC container via `SystemConnectorConfiguration` as follows;

``` java
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("eu.arrowhead")
public class SystemConnectorConfiguration {
    // ...

    @Value("${new_client_type:defaultImp}")
    private String implementationTypeOfNewClient;
    
    @Bean
    public NewClient newClient(@Autowired ArrowheadHelper arrowheadHelper) {
        if (implementationTypeOfNewClient.equals("defaultImp")) {
            return new NewClientDefaultImp(arrowheadHelper);
        }
        throw new UnsupportedOperationException("There is no any NewClient implementation by new_client_type!");
    }
    // ...
}
```

3. Unit tests should be added under the `nl.tue.systemconnectorpackage.clients.newClient.implementations.NewClientDefaultImp` class. Example unit tests can be examined within the project.