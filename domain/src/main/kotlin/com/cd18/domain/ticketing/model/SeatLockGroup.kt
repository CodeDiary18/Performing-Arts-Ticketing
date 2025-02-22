package com.cd18.domain.ticketing.model

import com.cd18.common.util.getCurrentTime
import java.time.LocalDateTime
import java.util.UUID

class SeatLockGroup(
    val lockGroupId: UUID,
    val userId: Long,
    val scheduleId: Long = 0L,
    val seatIds: List<Long>,
) {
    private val expireTimeDuration = 5L
    private val createTime: LocalDateTime = getCurrentTime()

    val expireTime: LocalDateTime
        get() = createTime.plusMinutes(expireTimeDuration)

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
