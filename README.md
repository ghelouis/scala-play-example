# Scala Play/Swagger sample project

Template of a basic full-fledged Scala backend application. In particular:
* a REST API self-documented with Swagger
* JSON request parsing to and from objects and with error handling
* separate layers for
  * outside world interfaces (controllers)
  * business logic (service)
  * database access (repository)
* data transfer objects (DTO) for data shared between the controller and service layer
* data access objects (DAO) for data shared between the service and the repository layer
* dependency injection
* unit tests

## Technologies
* Language: Scala
* Web framework: Play
* Dependency Injection: Guice (supported out of the box by Play)

## Usage
* Build and run the project: `sbt run`
* The server should then be accessible at <http://localhost:9000>
* The API documentation (Swagger UI) can be found at <http://localhost:9000/docs/>

## Resources
* Play: <https://developer.lightbend.com/start/?group=play&project=play-samples-play-scala-hello-world-tutorial>
* Play JSON mapping: <https://www.playframework.com/documentation/2.7.x/ScalaJsonAutomated>
* Dependency Injection: <https://www.playframework.com/documentation/2.7.x/ScalaDependencyInjection>
* Swagger Play plugin: <https://github.com/swagger-api/swagger-play> (choose the module corresponding to your play version)
* Swagger annotations: <https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X>
* Swagger Scala examples: <https://github.com/swagger-api/swagger-samples/tree/master/scala/scala-play2.6/app/controllers>
