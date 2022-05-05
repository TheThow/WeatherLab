package lab.weather.service

import lab.weather.data.LocationTemperature
import lab.weather.jpa.WeatherRepository
import org.springframework.stereotype.Service

@Service
class WeatherStatsService(val weatherRepository: WeatherRepository) {

    fun getAverageTemp() : List<LocationTemperature> {
        return weatherRepository.getAverageTemps()
    }

    fun getAverageTempbyLocation(location: String) : Double {
        return weatherRepository.getAverageTempsbyLocation(location)
    }

}