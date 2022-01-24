## Configuration Package

Contains modules used for configuration of the package. 2 modules are available.

1. `SystemConnectorConfiguration` : A [_Configuration_ class](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java-basic-concepts) which creates all beans of the _system-connector-package_. All dependencies of the _system-connector-package_ is managed by this module.

2. `SystemConnectorConfigurationConstants` : It provides some constant values to the clients of the package so that they can configure the package in their application.