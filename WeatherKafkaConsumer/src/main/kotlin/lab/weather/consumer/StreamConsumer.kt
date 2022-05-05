package lab.weather.consumer

import lab.weather.AggregatedWeatherInformationEvent
import lab.weather.LabConfiguration
import lab.weather.WeatherInformationEvent
import lab.weather.WeatherStationEvent
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.KTable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class StreamConsumer(private val labConfiguration: LabConfiguration) {

    @Autowired
    fun buildPipeline(streamsBuilder: StreamsBuilder) {
        val stationKTable: KTable<String, WeatherStationEvent> =
            streamsBuilder.stream<String, WeatherStationEvent>(labConfiguration.stationTopic)
                .toTable();

        val stream: KStream<String, WeatherInformationEvent> = streamsBuilder.stream(labConfiguration.weatherTopic)

        stream.join(stationKTable
            ) { weatherInfo, tableEntry ->
                AggregatedWeatherInformationEvent(
                    tableEntry.stationId,
                    tableEntry.name,
                    tableEntry.location,
                    weatherInfo.temperature,
                    weatherInfo.humidity,
                    weatherInfo.timestamp
                )
            }
            .to(labConfiguration.weatherProcessedTopic)
    }
}