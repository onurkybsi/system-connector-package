## Packing Structure

In the _system-connector-package_, there are 2 different kinds of packages; `eu.arrowhead.configuration`, `nl.tue.systemconnectorpackage`:

![PackingStructure](https://user-images.githubusercontent.com/54269270/150702484-9919aa6c-68dc-49a9-a3a6-895d5925e0d2.png)

* `eu.arrowhead.configuration`: A package contains only `ArrowheadInitializer` class which is a `ApplicationInitializer`. This package was built to initialize [_Arrowhead client library_](https://github.com/eclipse-arrowhead/application-library-java-spring). When the _system-connector-package_ initializes, it initializes _Arrowhead client library_ to access Arrowhead services.
* `nl.tue.systemconnectorpackage`: A package provides the clients of _MAAS, SAMOS, XAMA_. It contains 3 different subpackages:
    * [_Clients_](https://github.com/onurkybsi/system-connector-package/blob/master/doc/ClientsPackage.md)
    * [_Common_](https://github.com/onurkybsi/system-connector-package/blob/master/doc/CommonPackage.md)
    * [_Configuration_](https://github.com/onurkybsi/system-connector-package/blob/master/doc/ConfigurationPackage.md)
