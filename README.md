# system-connector-package

A Java library that provides clients to get services from _MAAS_ and _SAMOS_ systems.

## Installation

1. Add as a depedency in the POM (with appropriate repository configuration)

```xml
<dependency>
  <groupId>nl.tue</groupId>
  <artifactId>system-connector-package</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

2. If the _Arrowhead_ client(which is default client) will be used **and if** the system that will use _system-connector-package_ should be registered to _Arrowhead_, a _JSON_ file which contains metadata of the system should be declared under the `resources` folder, and its name should be added in `application.properties` (see  [Example system JSON file](https://github.com/onurkybsi/system-connector-package/blob/master/example-config-files/example-system-definition.json))
3. _system-connector-package_ needs some configuration values and it reads the from `application.properties`. These values should be satisfied by the client of library ([Example application.properties](https://github.com/onurkybsi/system-connector-package/blob/master/example-config-files/application.properties))
4. _system-connector-package_ uses _Spring Core_ to configure its dependicies so that _Spring_ should be initialized and `"nl.tue"` package should be scanned by _Spring_. As an example:
``` java
// Such component scanning will work
@SpringBootApplication
@ComponentScan(basePackages = SystemConnectorConfigurationConstants.PACKAGE_NAME_TO_BE_SCANNED_FOR_CONFIGURATION)
public class Initializer {
    public static void main(String[] args) {
        SpringApplication.run(Initializer.class, args);
    }
}
````

## Usage

After appropriate installation you can inject the clients via _Spring_

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
