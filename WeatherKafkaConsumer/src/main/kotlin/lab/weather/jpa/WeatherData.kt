package lab.weather.jpa

import javax.persistence.*

@Entity
@Table(name = "weatherdoc")
data class WeatherData(
    var stationId: Int?,
    var name: String?,
    var location: String?,
    var temperature: Float?,
    var humidity: Float?,
    var timestamp: Long?,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = 0
)

