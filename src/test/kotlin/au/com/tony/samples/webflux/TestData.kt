package au.com.tony.samples.webflux

import au.com.tony.samples.webflux.profile.*

object TestData {
    val agifyResponse = AgifyResponse(50)
    val genderizeResponse = GenderizeResponse("male")
    val countryProbabilityAU = CountryProbability("AU")
    val countryProbabilityUS = CountryProbability("US")
    val nationalizeResponse = NationalizeResponse(listOf(countryProbabilityAU, countryProbabilityUS))
    val nationalizeResponseWithEmptyCountryList = NationalizeResponse(emptyList())
    val profileDetails = ProfileDetails(50, "male", "AU")
}
