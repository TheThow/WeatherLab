package lab.weather

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory


@Configuration
@EnableKafka
open class KafkaConfiguration(val labConfiguration: LabConfiguration) {

    @Value(value = "\${kafka.bootstrapAddress}")
    lateinit var bootstrapAddress: String

    @Bean
    open fun weatherTopic(): NewTopic {
        return NewTopic(labConfiguration.weatherTopic, 1, 1.toShort())
    }

    @Bean
    open fun stationTopic(): NewTopic {
        return NewTopic(labConfiguration.stationTopic, 1, 1.toShort())
    }

    @Bean
    open fun kafkaAdmin(): KafkaAdmin {
        val configs: MutableMap<String, Any> = HashMap()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        return KafkaAdmin(configs)
    }

    @Bean
    open fun weatherProducerFactory(): ProducerFactory<String, WeatherInformationEvent> {
        return DefaultKafkaProducerFactory<String, WeatherInformationEvent>(producerConfig());
    }

    @Bean
    open fun stationProducerFactory(): ProducerFactory<String, WeatherStationEvent> {
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
    open fun weatherKafkaTemplate(): KafkaTemplate<String, WeatherInformationEvent> {
        return KafkaTemplate(weatherProducerFactory())
    }

    @Bean
    open fun stationKafkaTemplate(): KafkaTemplate<String, WeatherStationEvent> {
        return KafkaTemplate(stationProducerFactory())
    }
}