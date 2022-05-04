package lab.weather.controller

import lab.weather.data.WeatherInformation
import lab.weather.service.WeatherService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/weather")
class WeatherController(private val weatherService: WeatherService) {

    @PostMapping
    fun save(@RequestBody order: WeatherInformation) {
        return weatherService.save(order)
    }

}