package au.com.tony.samples.webflux.routes

import au.com.tony.samples.webflux.TestData
import au.com.tony.samples.webflux.profile.ProfileService
import au.com.tony.samples.webflux.profile.Routes
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@ExtendWith(SpringExtension::class)
@ContextConfiguration(
    classes = [
        Routes::class
    ]
)

@WebFluxTest
class RouterTest {

    @Autowired
    var webTestClient: WebTestClient? = null

    @MockBean
    lateinit var profileService: ProfileService


    @Test
    fun returnAccepted() {
        whenever(profileService.getProfileDetails("michael")).thenReturn(Mono.just(TestData.profileDetails))
        webTestClient!!.get()
            .uri("/api/profile/michael")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .equals(TestData.profileDetails)
    }
}
