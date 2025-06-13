package com.cd18.consumer

import com.cd18.application.member.MemberService
import com.cd18.application.performance.PerformanceInfoService
import com.cd18.application.ticketing.SeatService
import com.cd18.common.util.JsonUtils
import com.cd18.domain.ticketing.dto.ReserveInfoDto
import com.cd18.domain.ticketing.dto.ReserveSeatItemDto
import com.cd18.domain.ticketing.model.Ticket
import com.cd18.infra.mail.MailService
import com.cd18.infra.mail.template.ReserveConfirmTemplate
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class TicketConsumer(
    private val mailService: MailService,
    private val userService: MemberService,
    private val performanceService: PerformanceInfoService,
    private val seatService: SeatService,
) {
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
            val parsedTicketResult = JsonUtils.parseToResult<Ticket>(payload)
            val ticket: Ticket? = parsedTicketResult.getOrNull()
            if (ticket == null) {
                logger.warn(parsedTicketResult.exceptionOrNull()) { "❌ 파싱 실패: ${parsedTicketResult.exceptionOrNull()?.message}" }
                return
            }

            val user = userService.getById(ticket.userId).getOrThrow()
            val performance = performanceService.getById(ticket.performanceId).getOrThrow()
            val performanceSchedule =
                performanceService.getScheduleInfoById(
                    performanceId = ticket.performanceId,
                    scheduleIds = listOf(ticket.scheduleId),
                ).getOrThrow()
                    .first()
            val seatItems =
                seatService.getSeatsBySchedule(
                    performanceId = ticket.performanceId,
                    scheduleId = ticket.scheduleId,
                ).getOrThrow()
            mailService.sendByTemplate(
                to = user.email,
                template =
                    ReserveConfirmTemplate(
                        reserveInfo =
                            ReserveInfoDto(
                                performanceName = performance.performanceName,
                                performanceSchedule = performanceSchedule.scheduleDateTime,
                                seatItems =
                                    ticket.ticketItems.map { item ->
                                        ReserveSeatItemDto(
                                            seatId = item.seatId,
                                            seatName = seatItems.firstOrNull { it.seatId == item.seatId }?.seatName ?: "Unknown Seat",
                                        )
                                    },
                            ),
                    ),
            )
        }
        sb.append("═══════════════════════ BATCH END ═══════════════════════\n")

        logger.debug { sb.toString() }

        // TODO : sending email for ticket created
    }
}
