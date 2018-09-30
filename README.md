Java Test Case Study
====================

**API service using Java which retrieve the average forecast weather metrics of a specific city using [OpenWeatherMap](https://openweathermap.org)**

Prerequisites
-------------

* JDK 1.8 or newer ([Download](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html))
* Maven ([Download](https://maven.apache.org/download.cgi))

Build the project
-----------------

From the root of the project run

`mvnw package`

Run the application
-------------------

From the folder target of the project run

`java -jar weather-0.0.1-SNAPSHOT.jar`

Test the application
--------------------

Using curl:

`curl -X GET "http://localhost:8080/v1/weather/data?CITY=Milano" -H "accept: application/json"`
`curl -X GET "http://localhost:8080/v1/weather/data?CITY=Moscow" -H "accept: application/json"`

Using swagger: connect to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Dependencies
------------

* Spring Boot [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
* Jackson [https://github.com/FasterXML/jackson](https://github.com/FasterXML/jackson)
* Httpclient [https://hc.apache.org/](https://hc.apache.org/)
* Timeshape [https://github.com/RomanIakovlev/timeshape](https://github.com/RomanIakovlev/timeshape)
* Caffeine [https://github.com/ben-manes/caffeine](https://github.com/ben-manes/caffeine)
* Springfox-swagger2 [https://springfox.github.io/springfox/](https://springfox.github.io/springfox/)

Challenges and decisions
------------------------

The /data API includes a single CITY parameter with the name of the city. Since there could be more than one city with the same name I decided to return an object including an array of all the cities matching the given parameter. Each item of the array includes the country and the geographical coordinates of the city to identify it among the others.

At the startup the CityIdService load a map of all the city names from an included json provided by OpenWeatherMap with all the ids matching it, so to use the ID API which is the suggested way. Also initialized is the Timeshape library which maps geocoordinates with time zones since the service returns UTC times and we need the local ones to disclaim between daily and nightly temperatures.

**Input validation**: The lone parameter CITY is validated using the custom tag @ValidCity which tests if the name is included in the cities map, otherwise it immediately returns bad request (400). Other validations are mostly performed by Spring (absent parameter or other different calls).

**API docs**: Swagger annotations has been used to document the controller. The documentation can be accessed from [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

**Tests**: For each service a unit test class has been developed each containing different scenarios and use cases. For services using other services they have been mocked using [Mockito](https://site.mockito.org). Also an integration test have been included calling directly the controller and all the services. It should be noted that usually integration tests should not be called together with unit tests.

**Caching**: The Spring caching framework has been used to cache the result of a call to OpenWeatherMap for a given city id. The cache TTL has been set to 30 minutes to avoid caching outdated data. For the sake of simplicity [Caffeine](https://github.com/ben-manes/caffeine) embedded cache implementation has been used. Obviously for production, multi instance and scalable environments a centralized cache should be used, like [Redis](https://redis.io/) or similar.