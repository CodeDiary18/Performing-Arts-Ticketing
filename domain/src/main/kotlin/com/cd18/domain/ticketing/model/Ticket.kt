package com.cd18.domain.ticketing.model

import com.cd18.common.util.formattedTime
import com.cd18.common.util.generateRandomString
import com.cd18.common.util.getCurrentTime
import java.time.LocalDateTime

data class Ticket(
    val userId: Long,
    val performanceId: Long,
    val scheduleId: Long,
    val seatIds: List<Long> = emptyList(),
    val createTime: LocalDateTime = getCurrentTime(),
    val ticketNo: String = generateTicketNo(createTime),
    val ticketItems: List<TicketItem> =
        seatIds.mapIndexed {
                index,
                seatId,
            ->
            TicketItem(seatId, "$ticketNo-${index.toString().padStart(3, '0')}")
        },
) {
    data class TicketItem(val seatId: Long, val ticketItemNo: String)

    companion object {
        fun generateTicketNo(createTime: LocalDateTime): String {
            return "T${createTime.formattedTime("yyMMddHH")}${generateRandomString(6, uppercase = true)}"
        }

        fun create(
            userId: Long,
            performanceId: Long,
            scheduleId: Long,
            seatIds: List<Long>,
        ): Ticket {
            return Ticket(
                userId = userId,
                performanceId = performanceId,
                scheduleId = scheduleId,
                seatIds = seatIds,
            )
        }
    }
}
