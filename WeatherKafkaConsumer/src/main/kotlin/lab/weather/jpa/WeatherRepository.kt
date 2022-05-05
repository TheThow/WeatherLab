package lab.weather.jpa

import lab.weather.data.LocationTemperature
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.xml.stream.Location
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Repository
interface WeatherRepository : CrudRepository<WeatherData, Long> {

    fun findByStationId(stationId: Int) : Iterator<WeatherData>

    @Query("SELECT new lab.weather.data.LocationTemperature(location, avg(temperature)) from WeatherData GROUP BY location")
    fun getAverageTemps() : List<LocationTemperature>


    @Query("SELECT avg(temperature) from WeatherData Where location = :#{#location} GROUP BY location")
    fun getAverageTempsbyLocation(location: String) : Double
}