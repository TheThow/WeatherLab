package lab.weather.producer

import lab.weather.WeatherInformationEvent
import lab.weather.data.WeatherInformation
import org.springframework.stereotype.Component
import org.springframework.cloud.stream.messaging.Source
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder

@Component
class WeatherProducer(private val source: Source) {

    fun publishWeatherInformation(weather: WeatherInformation) {
        source.output().send(
            MessageBuilder.withPayload(WeatherInformationEvent(weather.stationId, weather.temperature, weather.humidity))
            .setHeader(KafkaHeaders.MESSAGE_KEY, weather.stationId).build())
    }

}