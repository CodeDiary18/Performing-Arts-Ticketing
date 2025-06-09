package com.cd18.consumer.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@Configuration
class KafkaConsumerConfig(
    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapServers: String,
) {
    @Bean
    fun stringConsumerFactory(): ConsumerFactory<String, String> {
        val props =
            mapOf<String, Any>(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to true,
            )
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun stringBatchKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        return ConcurrentKafkaListenerContainerFactory<String, String>().apply {
            consumerFactory = stringConsumerFactory()
            isBatchListener = true
            setConcurrency(1)
            containerProperties.pollTimeout = 5000
        }
    }
}
