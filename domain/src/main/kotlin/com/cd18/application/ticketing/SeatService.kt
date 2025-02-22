package com.cd18.application.ticketing

import com.cd18.domain.ticketing.model.Seat
import com.cd18.domain.ticketing.model.SeatLockGroup
import java.util.UUID

interface SeatService {
    fun getSeatsBySchedule(
        performanceId: Long,
        scheduleId: Long,
    ): Result<List<Seat>>

    fun holdingSeats(
        userId: Long,
        performanceId: Long,
        scheduleId: Long,
        seatIds: List<Long>,
    ): Result<SeatLockGroup>

    fun cancelSeatLockGroup(
        userId: Long,
        lockGroupId: UUID,
    ): Result<Unit>
}
