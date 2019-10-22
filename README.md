# Application Submission System using Kafka
Using Kafka developing an application submission system.

### Steps to run the App
* Configure the `application.properties` file with kafka configs etc.
* Run `mvn clean install` to build the application.
* Token Generation Counter (1st set of counters) can be created by using class `TokenGenerationCounter`.
* Service Counters (2nd set of counters) can be created by classes `NormalServiceCounter` or `PremiumServiceCounter`.

_Note:_ 

* By default the application polls kafka queue for `100` seconds to fetch the data.
* There is a `RunApp` class which demos how to use the app
* Logger has been configured to log error in the `logs` folder created at project directory level.
