package lab.weather.producer

import lab.weather.LabConfiguration
import lab.weather.WeatherInformationEvent
import lab.weather.WeatherStationEvent
import lab.weather.data.WeatherInformation
import lab.weather.data.WeatherStation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer(val labConfiguration: LabConfiguration) {

    @Autowired
    lateinit var weatherKafkaTemplate: KafkaTemplate<String, WeatherInformationEvent>

    @Autowired
    lateinit var stationKafkaTemplate: KafkaTemplate<String, WeatherStationEvent>

    fun publishWeatherInformation(weather: WeatherInformation) {
        weatherKafkaTemplate.send(labConfiguration.weatherTopic,
            weather.stationId.toString(),
            WeatherInformationEvent(
                weather.stationId,
                weather.temperature,
                weather.humidity,
                weather.timestamp
            )
        )
    }

    fun updateStationInformation(station: WeatherStation) {
        stationKafkaTemplate.send(labConfiguration.stationTopic,
            station.stationId.toString(),
            WeatherStationEvent(
                station.stationId,
                station.name,
                station.position
            ))
    }
}