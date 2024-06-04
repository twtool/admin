package icu.twtool

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("icu.twtool.**.properties")
class AdminApplication

fun main(vararg args: String) {
    runApplication<AdminApplication>(*args) {
        setBanner(AdminBanner())
    }
}