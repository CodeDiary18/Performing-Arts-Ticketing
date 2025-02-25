package com.cd18.domain.ticketing.model

import com.cd18.common.util.formattedTime
import com.cd18.common.util.generateRandomString
import com.cd18.common.util.getCurrentTime
import java.time.LocalDateTime

class Ticket(
    val userId: Long,
    val performanceId: Long,
    val scheduleId: Long,
    private val seatIds: List<Long>,
) {
    private val createTime: LocalDateTime = getCurrentTime()
    val ticketNo: String = generateTicketNo()
    val ticketItems: List<TicketItem> = seatIds.mapIndexed { index, seatId -> TicketItem(seatId, index + 1) }

    inner class TicketItem(val seatId: Long, index: Int) {
        val ticketItemNo: String = "${this@Ticket.ticketNo}-${index.toString().padStart(3, '0')}"
    }

    companion object {
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

    private fun generateTicketNo(): String {
        return "T${createTime.formattedTime("yyMMddHH")}${generateRandomString(6, uppercase = true)}"
    }
}
