# Scala Play sample project
[Try it here](https://scalaplayexample.herokuapp.com/docs/)

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
* Database mapping: Slick 3.2
* Database: H2 (in-memory)

## Usage
* Build and run the project: `sbt run`
* The server should then be accessible at <http://localhost:9000>
* The API documentation (Swagger UI) can be found at <http://localhost:9000/docs/>
* Run tests: `sbt test`

## Versions
* [v1.1](https://github.com/ghelouis/scala-play-example/releases/tag/v1.1): H2 in-memory database and Slick integration
* [v1.0](https://github.com/ghelouis/scala-play-example/releases/tag/v1.0): mocked database (no H2/Slick)

## Resources
* Play: <https://developer.lightbend.com/start/?group=play&project=play-samples-play-scala-hello-world-tutorial>
* Play JSON mapping: <https://www.playframework.com/documentation/2.7.x/ScalaJsonAutomated>
* Dependency Injection: <https://www.playframework.com/documentation/2.7.x/ScalaDependencyInjection>
* Swagger Play plugin: <https://github.com/swagger-api/swagger-play> (choose the module corresponding to your play version)
* Swagger annotations: <https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X>
* Swagger Scala examples: <https://github.com/swagger-api/swagger-samples/tree/master/scala/scala-play2.6/app/controllers>
* Slick: <https://www.playframework.com/documentation/2.7.x/PlaySlick>

## Known issues
Requests other than GET made via Swagger UI get rejected. It seems to have something to do with CSRF...
This is specific to Swagger, for instance with Postman or curl POST requests work as expected. :(
