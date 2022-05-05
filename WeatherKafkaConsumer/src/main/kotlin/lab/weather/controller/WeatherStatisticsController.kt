package lab.weather.controller

import com.fasterxml.jackson.databind.ObjectMapper
import lab.weather.service.WeatherStatsService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import java.net.ResponseCache
import javax.xml.stream.Location

@Controller
class WeatherStatisticsController(val weatherStatsService: WeatherStatsService) {

    val mapper: ObjectMapper = ObjectMapper()

    @GetMapping("temperature")
    fun getAverageTemperature() : ResponseEntity<String> {
        val temps = weatherStatsService.getAverageTemp()
        return ResponseEntity.ok(mapper.writeValueAsString(temps))
    }

    @GetMapping("temperature/{location}")
    fun getAverageTemperaturebyLocation(@PathVariable location: String) : ResponseEntity<String> {
        val temps = weatherStatsService.getAverageTempbyLocation(location)
        return ResponseEntity.ok(mapper.writeValueAsString(temps))
    }

}