package lab.weather

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.schema.client.EnableSchemaRegistryClient
import org.springframework.cloud.stream.messaging.Source

@EnableSchemaRegistryClient
@EnableBinding(Source::class)
@SpringBootApplication
class WeatherProducerApplication

fun main(args: Array<String>) {
    runApplication<WeatherProducerApplication>(*args)
}