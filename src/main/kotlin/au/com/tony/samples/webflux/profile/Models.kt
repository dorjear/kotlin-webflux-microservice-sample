package au.com.tony.samples.webflux.profile

data class AgifyResponse(val age: Int?)

data class GenderizeResponse(val gender: String?)

data class NationalizeResponse(val country: List<CountryProbability>?)

data class CountryProbability(val country_id: String)

data class ProfileDetails(
    val age: Int?,
    val gender: String?,
    val nationality: String?
)
