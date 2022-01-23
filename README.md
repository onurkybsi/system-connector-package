# system-connector-package

A Java library that provides client modules to consume services from _MAAS_, _SAMOS_ and _XAMA_ systems.

## Installation

1. Add _system-connector-package_ as a depedency in the POM (with appropriate repository configuration)

```xml
<dependency>
  <groupId>nl.tue</groupId>
  <artifactId>system-connector-package</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

2. _system-connector-package_ needs some configuration values and it reads the from `application.properties`. These values must be satisfied. ([Example application.properties](https://github.com/onurkybsi/system-connector-package/blob/master/example-config-files/application.properties))

3. _system-connector-package_ uses _Spring Boot_ to configure its dependicies so that _Spring Boot_ must be initialized and `"nl.tue"` package should be scanned by _Spring_. As an example:
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