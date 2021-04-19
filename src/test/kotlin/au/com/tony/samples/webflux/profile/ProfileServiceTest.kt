package au.com.tony.samples.webflux.profile

import au.com.tony.samples.webflux.TestData
import au.com.tony.samples.webflux.config.ConfigProperties
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class ProfileServiceTest {
    private val restApiClient: RestApiClient = mock()
    private val configProperties = ConfigProperties("http://getAgeUrl/?name=", "http://getGenderUrl/?name=", "http://getNationalityUrl/?name=")
    private val nameService = ProfileService(restApiClient, configProperties)
    private val name = "michael"
    @Test
    fun normalCase(){
        whenever(restApiClient.get(configProperties.getAgeUrl+name, AgifyResponse::class.java)).thenReturn(Mono.just(
            TestData.agifyResponse))
        whenever(restApiClient.get(configProperties.getGenderUrl+name, GenderizeResponse::class.java)).thenReturn(Mono.just(
            TestData.genderizeResponse))
        whenever(restApiClient.get(configProperties.getNationalityUrl+name, NationalizeResponse::class.java)).thenReturn(Mono.just(
            TestData.nationalizeResponse))

        StepVerifier.create(nameService.getProfileDetails(name))
            .expectNext(TestData.profileDetails)
            .verifyComplete()
    }

    @Test
    fun emptyCountryList(){
        whenever(restApiClient.get(configProperties.getAgeUrl+name, AgifyResponse::class.java)).thenReturn(Mono.just(
            TestData.agifyResponse))
        whenever(restApiClient.get(configProperties.getGenderUrl+name, GenderizeResponse::class.java)).thenReturn(Mono.just(
            TestData.genderizeResponse))
        whenever(restApiClient.get(configProperties.getNationalityUrl+name, NationalizeResponse::class.java)).thenReturn(Mono.just(
            TestData.nationalizeResponseWithEmptyCountryList))

        StepVerifier.create(nameService.getProfileDetails(name))
            .expectNext(TestData.profileDetails.copy(nationality = null))
            .verifyComplete()
    }

}
