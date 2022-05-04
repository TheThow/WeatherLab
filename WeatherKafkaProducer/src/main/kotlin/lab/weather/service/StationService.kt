package lab.weather.service

import lab.weather.data.WeatherStation
import lab.weather.producer.KafkaProducer
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class StationService(val producer: KafkaProducer) {

    fun updateStation(station: WeatherStation) {
        producer.updateStationInformation(station)
    }

    @PostConstruct
    fun setup() {
        producer.updateStationInformation(WeatherStation(1, "Karl", "Berlin/Germany"))
        producer.updateStationInformation(WeatherStation(2, "Hans", "Munich/Germany"))
        producer.updateStationInformation(WeatherStation(3, "Baguette", "Paris/France"))
    }

}