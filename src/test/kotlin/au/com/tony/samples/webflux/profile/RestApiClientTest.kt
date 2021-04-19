package au.com.tony.samples.webflux.profile

import au.com.tony.samples.webflux.TestData
import au.com.tony.samples.webflux.config.WebClientConfig
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockserver.integration.ClientAndServer
import org.mockserver.junit.jupiter.MockServerExtension
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.mockserver.model.MediaType
import reactor.test.StepVerifier

@ExtendWith(MockServerExtension::class)
class RestApiClientTest(private val client: ClientAndServer) {

    private val webClient = WebClientConfig().webClient()
    private val objectMapper = jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    private val restApiClient = RestApiClient(webClient)
    private val testUrl = "http://localhost:${client.port}/test"

    @BeforeEach
    fun beforeEach() {
        client.reset()
    }

    @Test
    fun verifyDocument() {
        client.`when`(
            HttpRequest.request()
                .withMethod("GET")
                .withPath("/test")
        ).respond(HttpResponse().withContentType(MediaType.APPLICATION_JSON).withStatusCode(200).withBody(objectMapper.writeValueAsString(
            TestData.agifyResponse)))
        val response = restApiClient.get(testUrl, AgifyResponse::class.java)
        StepVerifier.create(response)
            .expectNext(TestData.agifyResponse)
            .verifyComplete()
    }

    @Test
    fun verifyDocumentError() {
        client.`when`(
            HttpRequest.request()
                .withMethod("GET")
                .withPath("/test")
        )
            .respond(HttpResponse().withStatusCode(503).withBody(objectMapper.writeValueAsString("some error")))

        val response = restApiClient.get(testUrl, AgifyResponse::class.java)
        StepVerifier.create(response)
            .expectError(RuntimeException::class.java)
            .verify()
    }

    @Test
    fun verifyDocumentBadResponse() {
        client.`when`(
            HttpRequest.request()
                .withMethod("GET")
                .withPath("/test")
        )
            .respond(HttpResponse().withStatusCode(200).withBody(objectMapper.writeValueAsString("some bad response")))

        val response = restApiClient.get(testUrl, AgifyResponse::class.java)
        StepVerifier.create(response)
            .expectError(RuntimeException::class.java)
            .verify()
    }

    @Test
    fun verifyErrorWithoutBody() {
        val response = restApiClient.get(testUrl, AgifyResponse::class.java)
        StepVerifier.create(response)
            .expectError(RuntimeException::class.java)
            .verify()
    }

}
