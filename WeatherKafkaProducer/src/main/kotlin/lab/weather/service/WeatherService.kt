package lab.weather.service

import lab.weather.data.WeatherInformation
import lab.weather.producer.KafkaProducer
import org.springframework.stereotype.Service

@Service
class WeatherService(val producer: KafkaProducer) {

    fun save(weather: WeatherInformation) {
        producer.publishWeatherInformation(weather);
    }

}