package com.cd18.infra.kafka

import com.cd18.domain.port.MessagePublisher
import com.cd18.domain.port.enums.MessageTopic
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducerService(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
) : MessagePublisher {
    private val logger = KotlinLogging.logger {}

    override fun send(
        topic: MessageTopic,
        key: String,
        message: Any,
    ) {
        kafkaTemplate.send(topic.toString(), key, message)
            .thenAccept { result ->
                logger.debug { "Kafka send success. topic=$topic, key=$key, offset=${result.recordMetadata.offset()}" }
            }.exceptionally { ex ->
                logger.error { "Kafka send failed. topic=$topic, key=$key, error=${ex.message}" }
                null
            }
    }
}
