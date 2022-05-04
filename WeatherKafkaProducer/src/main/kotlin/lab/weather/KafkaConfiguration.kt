package lab.weather

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import io.confluent.kafka.serializers.KafkaAvroSerializer
import lab.weather.data.WeatherInformation
import lab.weather.data.WeatherStation
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.streams.StreamsConfig.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration
import org.springframework.kafka.config.KafkaStreamsConfiguration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer


@Configuration
@EnableKafka
class KafkaConfiguration(val labConfiguration: LabConfiguration) {

    @Value(value = "\${kafka.bootstrapAddress}")
    lateinit var bootstrapAddress: String

    @Bean
    fun weatherTopic(): NewTopic {
        return NewTopic(labConfiguration.weatherTopic, 1, 1.toShort())
    }

    @Bean
    fun stationTopic(): NewTopic {
        return NewTopic(labConfiguration.stationTopic, 1, 1.toShort())
    }

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs: MutableMap<String, Any> = HashMap()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        return KafkaAdmin(configs)
    }

    @Bean(name = [KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME])
    fun kStreamsConfig(): KafkaStreamsConfiguration? {
        val props: MutableMap<String, Any> = HashMap()
        props[APPLICATION_ID_CONFIG] = "station-app"
        props[BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.String().javaClass.name
        props[DEFAULT_VALUE_SERDE_CLASS_CONFIG] = JsonSerializer::class.java
        return KafkaStreamsConfiguration(props)
    }

    @Bean
    fun weatherProducerFactory(): ProducerFactory<String, WeatherInformationEvent> {
        return DefaultKafkaProducerFactory<String, WeatherInformationEvent>(producerConfig());
    }

    @Bean
    fun stationProducerFactory(): ProducerFactory<String, WeatherStationEvent> {
        return DefaultKafkaProducerFactory<String, WeatherStationEvent>(producerConfig());
    }

    private fun producerConfig() : MutableMap<String, Any> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaAvroSerializer::class.java
        configProps[AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG] = "http://localhost:8081"
        return configProps
    }

    @Bean
    fun weatherKafkaTemplate(): KafkaTemplate<String, WeatherInformationEvent> {
        return KafkaTemplate(weatherProducerFactory())
    }

    @Bean
    fun stationKafkaTemplate(): KafkaTemplate<String, WeatherStationEvent> {
        return KafkaTemplate(stationProducerFactory())
    }
}