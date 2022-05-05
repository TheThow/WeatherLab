package lab.weather.data

data class WeatherInformation(
    val stationId: Int,
    val temperature: Float,
    val humidity: Float,
    val timestamp: Long
)