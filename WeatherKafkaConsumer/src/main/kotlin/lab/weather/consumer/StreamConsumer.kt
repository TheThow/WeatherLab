package lab.weather.consumer

import io.confluent.kafka.serializers.KafkaAvroDeserializer
import lab.weather.LabConfiguration
import lab.weather.WeatherInformationEvent
import org.apache.kafka.common.requests.MetadataRequest.Builder.allTopics
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Predicate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*


@Component
class StreamConsumer(val labConfiguration: LabConfiguration) {

    @Autowired
    fun buildPipeline(streamsBuilder: StreamsBuilder) {
        val stream: KStream<String, WeatherInformationEvent> = streamsBuilder.stream(labConfiguration.weatherTopic)

        stream.filter { id, weatherInformationEvent ->  weatherInformationEvent.temperature < 100}
            .to(labConfiguration.weatherProcessedTopic)
    }
}