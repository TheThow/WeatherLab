package lab.weather.controller

data class WeatherInformationRequest(
    val stationId: Int,
    val temperature: Float,
    val humidity: Float,
    val timestamp: Long?
)