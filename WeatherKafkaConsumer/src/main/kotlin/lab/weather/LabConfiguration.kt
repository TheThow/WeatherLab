package lab.weather

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("lab")
@ConstructorBinding
class LabConfiguration(
    val weatherTopic: String,
    val weatherProcessedTopic: String,
    val stationTopic: String
)