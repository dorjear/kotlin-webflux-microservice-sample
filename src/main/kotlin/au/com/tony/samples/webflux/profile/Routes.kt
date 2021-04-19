package au.com.tony.samples.webflux.profile

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router

@Configuration
class Routes(private val profileService: ProfileService) {

    @Bean
    fun route() = router {
        ("/api/").nest {
            GET("profile/{name}") {
                ServerResponse.ok().body(profileService.getProfileDetails(it.pathVariable("name")))
            }
        }
    }

}
