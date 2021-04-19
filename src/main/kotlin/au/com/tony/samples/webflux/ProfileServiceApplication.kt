package au.com.tony.samples.webflux

import org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [ReactiveSecurityAutoConfiguration::class, ReactiveManagementWebSecurityAutoConfiguration::class])
class ProfileServiceApplication

fun main(args: Array<String>) {
    runApplication<ProfileServiceApplication>(*args)
}
