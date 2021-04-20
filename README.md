# kotlin-webflux-microservice-sample

This is a sample of typical spring boot webflux microservice.

This sample is based on a KISS principle coding style (https://en.wikipedia.org/wiki/KISS_principle)

This sample start a http service to serve some rest APIs without authentication

API details please refer to ./openapi.yaml

This sample demos the features and usage following technologies:
- Kotlin + Spring boot
- Reactive API with webflux functional endpoint
- Invoking downstream APIs with reactive web client
- Json logging with Logstash
- Health endpoint with actuator e.g http://localhost:8080/actuator/health
- Tracing in microservices with Spring Cloud Sleuth
- Open API documentation
- Unit testing coverage verification with Jacoco

Features to be added to this demo:
- Kotlin style check ktlin
- Dependency vulnerability check
- Add some post endpoints with request object validation
- General exception handling
- Generating Request and Response objects from openapi
- Auditing the incoming and outgoing requests and responses
- Dockerize
- Sonarqube check
- Reactive API in Controller style

To clone this sample please run command `git clone https://github.com/dorjear/kotlin-webflux-microservice-sample.git`
To run on local please run command `./gradlew bootRun`

To build the artifact please run command `./gradlew clean build`
