package com.cd18.domain.ticketing.model

import java.time.LocalDateTime
import java.util.UUID

class SeatLockGroup(
    val lockGroupId: UUID,
    val userId: Long,
    val scheduleId: Long,
    val seatIds: List<Long>,
) {
    private val expireTimeDuration = 5L

    val expireTime: LocalDateTime = LocalDateTime.now().plusMinutes(expireTimeDuration)

    companion object {
        fun create(
            userId: Long,
            scheduleId: Long,
            seatIds: List<Long>,
        ): SeatLockGroup {
            return SeatLockGroup(
                lockGroupId = UUID.randomUUID(),
                userId = userId,
                scheduleId = scheduleId,
                seatIds = seatIds,
            )
        }
    }
}
