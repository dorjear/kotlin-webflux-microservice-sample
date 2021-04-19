package au.com.tony.samples.webflux.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
class WebClientConfig {

    @Bean
    fun webClient(): WebClient {
        return WebClient
            .create()
            .mutate()
            .clientConnector(ReactorClientHttpConnector(HttpClient.create().wiretap(true)))
            .build()
    }
}
