package au.com.tony.samples.webflux.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app.external")
data class ConfigProperties(
    var getAgeUrl: String = "",
    var getGenderUrl: String = "",
    var getNationalityUrl: String = ""
)
