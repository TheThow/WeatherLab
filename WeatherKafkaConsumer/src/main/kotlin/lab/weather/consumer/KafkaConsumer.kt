package lab.weather.consumer

import lab.weather.WeatherInformationEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer {

    @KafkaListener(topics = ["weather"], groupId = "weather_processor")
    fun processMessage(weatherInformation: WeatherInformationEvent) {
        println("RECEIVED: ${weatherInformation}")
    }

}