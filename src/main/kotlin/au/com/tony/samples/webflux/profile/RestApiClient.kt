package au.com.tony.samples.webflux.profile


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class RestApiClient(private val webClient: WebClient) {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun <T> get(uri: String, clazz: Class<T>): Mono<T> {
        return webClient
            .get()
            .uri(uri)
            .exchangeToMono { handleResponse(it, uri, clazz) }
    }

    private fun <T> handleResponse(response: ClientResponse, uri: String, clazz: Class<T>): Mono<T> {
        return when {
            response.statusCode().is2xxSuccessful -> {
                response.bodyToMono(clazz).doOnNext{logger.info(it.toString())}
            }
            else -> {
                response.bodyToMono(String::class.java).flatMap { body ->
                    logger.error("Calling uri $uri fails with status code ${response.rawStatusCode()} and error message $body")
                    Mono.error<T>(RuntimeException("Check Failure"))
                }.switchIfEmpty(Mono.error<T>(RuntimeException("Check Failure")))
            }
        }
    }
}
