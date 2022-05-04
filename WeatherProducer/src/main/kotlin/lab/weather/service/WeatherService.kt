package lab.weather.service

import lab.weather.data.WeatherInformation
import lab.weather.producer.WeatherProducer
import org.springframework.stereotype.Service

@Service
class WeatherService(val producer: WeatherProducer) {

    fun save(weather: WeatherInformation) {
        producer.publishWeatherInformation(weather);
    }

}