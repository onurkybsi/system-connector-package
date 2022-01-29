# system-connector-package

A Java library that provides client modules to consume services from _MAAS_, _SAMOS_ and _XAMA_ systems.

## System Requirements
* [Maven](https://maven.apache.org/)
* [JDK/JRE 11](https://www.oracle.com/java/technologies/downloads/#java11)

## Installation

1. Use _Maven_ to build _system-connector-package_

```bash
mvn install
```

2. Add _system-connector-package_ as a depedency in the POM (with appropriate repository configuration)

```xml
<dependency>
  <groupId>nl.tue</groupId>
  <artifactId>system-connector-package</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

3. _system-connector-package_ needs some configuration values and it reads the from `application.properties`. These values must be satisfied. ([Example application.properties](https://github.com/onurkybsi/system-connector-package/blob/master/doc/example-config-files/application.properties))

4. _system-connector-package_ uses _Spring Boot_ to configure its dependicies so that _Spring Boot_ must be initialized and `"nl.tue"` package should be scanned by _Spring_. As an example:
``` java
// Such component scanning will work
@SpringBootApplication(scanBasePackages = {
        SystemConnectorConfigurationConstants.PACKAGE_NAME_TO_BE_SCANNED_FOR_CONFIGURATION })
public class Initializer {
    public static void main(String[] args) {
        SpringApplication.run(Initializer.class, args);
    }
}
````

5. [**OPTIONAL**] _Version 1.0.0_ uses Arrowhead framework to connect systems so that your system must be registered in _Arrowhead framework_. You can handle this job by using _Arrowhead's API_ or inserting necessary entries to the _Arrowhead_ database. However, you can use [ArrowheadHelper](https://github.com/onurkybsi/system-connector-package/blob/master/src/main/java/nl/tue/systemconnectorpackage/clients/utilities/arrowhead/ArrowheadHelper.java) which is a module that provides various helper services to handle Arrowhead. You can register your system at the initialization stage of your system as below:
``` java
/**
 * Handles initialization stuff
 */
@Component
public class SystemAInitializer implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * Registers SystemA services to Arrowhead in the initialization
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ArrowheadHelper helper = event.getApplicationContext().getBean(ArrowheadHelper.class);
        helper.registerSystemsToArrowhead("/systema-service-definitions.json");
    }
}
````
Method `registerSystemsToArrowhead` takes `resource` path of your system's services descriptive JSON file as parameter. You can check the [example system services descriptive JSON file](https://github.com/onurkybsi/system-connector-package/blob/master/doc/example-config-files/example-system-definition.json). So, you can create such a JSON file in your `resources` and with `registerSystemsToArrowhead` service of `ArrowheadHelper` you can register your system to _Arrowhead_.

## Usage

After appropriate installation you can inject the clients provided by _system-connector-package_ via _Spring Boot_

As an example;
``` java
@Component
public class ExampleClientModule {
  @Autowired
  private MAASClient maasClient;
  
  public HashMap<String, Object> invokeFilterModelService() {
    return maasClient.getModelFilterClient().filterModel("test", "test", "test", "test");
  }
}
```

## Documentation

* [Packaging structure](https://github.com/onurkybsi/system-connector-package/blob/master/doc/PackingStructure.md)
* [_Clients_ Package](https://github.com/onurkybsi/system-connector-package/blob/master/doc/ClientsPackage.md)
* [_Common_ Package](https://github.com/onurkybsi/system-connector-package/blob/master/doc/CommonPackage.md)
* [_Configuration_ Package](https://github.com/onurkybsi/system-connector-package/blob/master/doc/ConfigurationPackage.md)
* [How to do?](https://github.com/onurkybsi/system-connector-package/blob/master/doc/HowToDo.md)
