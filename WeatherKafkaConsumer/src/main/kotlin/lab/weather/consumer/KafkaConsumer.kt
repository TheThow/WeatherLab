package lab.weather.consumer

import lab.weather.AggregatedWeatherInformationEvent
import lab.weather.LabConfiguration
import lab.weather.WeatherInformationEvent
import lab.weather.jpa.WeatherData
import lab.weather.jpa.WeatherRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer(
    private val labConfiguration: LabConfiguration,
    private val weatherRepository: WeatherRepository
    ) {

    @KafkaListener(topics = ["weather-processed"], groupId = "weather_processor")
    fun processMessage(weatherInformation: AggregatedWeatherInformationEvent) {
        weatherRepository.save(WeatherData(
            weatherInformation.stationId,
            weatherInformation.name.toString(),
            weatherInformation.location.toString(),
            weatherInformation.temperature,
            weatherInformation.humidity,
            weatherInformation.timestamp
        ))
    }

}