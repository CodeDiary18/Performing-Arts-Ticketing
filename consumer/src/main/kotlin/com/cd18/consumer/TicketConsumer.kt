package com.cd18.consumer

import com.cd18.common.util.JsonUtils
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class TicketConsumer {
    private val logger = KotlinLogging.logger {}

    @KafkaListener(
        topics = ["ticket-create"],
        groupId = "ticket-create-batch-group",
        containerFactory = "stringBatchKafkaListenerContainerFactory",
    )
    fun consumeBatch(messages: List<ConsumerRecord<String, String>>) {
        val sb = StringBuilder()
        sb.append("\n══════════════════════ BATCH START ══════════════════════\n")
        sb.append("📦 Received batch of tickets: ${messages.size}\n")
        sb.append("---------------------------------------------------------\n")

        messages.forEachIndexed { index, record ->
            sb.append(" \uD83D\uDCAC Message #${index + 1}\n")
            sb.append("  🔑 Key      : ${record.key()}\n")
            sb.append("  📂 Partition: ${record.partition()}\n")
            sb.append("  📍 Offset   : ${record.offset()}\n")

            val headersMap =
                record.headers().associate { header ->
                    header.key() to String(header.value())
                }

            if (headersMap.isNotEmpty()) {
                sb.append("  📋 Headers  :\n")
                headersMap.forEach { (key, value) ->
                    sb.append("   📌 $key: $value\n")
                }
            }

            val payload = record.value()
            if (payload.isNullOrBlank()) {
                sb.append("❌ ERROR: Payload is null or blank\n")
            } else {
                sb.append("  📄 Payload:\n")
                val prettyJson = JsonUtils.prettyPrintJson(payload)
                prettyJson.lineSequence().forEach { line ->
                    sb.append("    $line\n")
                }
            }
            sb.append("---------------------------------------------------------\n")
        }
        sb.append("═══════════════════════ BATCH END ═══════════════════════\n")

        logger.debug { sb.toString() }

        // TODO : sending email for ticket created
    }
}
