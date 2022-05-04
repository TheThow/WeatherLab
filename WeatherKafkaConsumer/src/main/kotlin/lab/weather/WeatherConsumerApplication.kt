package lab.weather

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
open class WeatherConsumerApplication

fun main(args: Array<String>) {
    runApplication<WeatherConsumerApplication>(*args)
}