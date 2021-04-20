package au.com.tony.samples.webflux.profile


import au.com.tony.samples.webflux.config.ConfigProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ProfileService(private val restApiClient: RestApiClient, private val configProperties: ConfigProperties) {
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun getProfileDetails(name: String): Mono<ProfileDetails> {
        logger.debug("Got name: $name")
        val agifyResponseMono =  restApiClient.get("${configProperties.getAgeUrl}$name", AgifyResponse::class.java)
        val genderizeResponseMono =  restApiClient.get("${configProperties.getGenderUrl}$name", GenderizeResponse::class.java)
        val nationalizeResponseMono =  restApiClient.get("${configProperties.getNationalityUrl}$name", NationalizeResponse::class.java)
        return Mono.zip(agifyResponseMono, genderizeResponseMono, nationalizeResponseMono).map {
            ProfileDetails(it.t1.age, it.t2.gender, it.t3.country?.firstOrNull()?.country_id)
        }
    }
}
