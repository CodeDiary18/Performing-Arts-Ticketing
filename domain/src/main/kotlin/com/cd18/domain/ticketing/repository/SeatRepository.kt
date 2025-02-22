package com.cd18.domain.ticketing.repository

import com.cd18.domain.ticketing.enums.SeatStatus
import com.cd18.domain.ticketing.model.Seat
import com.cd18.domain.ticketing.model.SeatHolding

interface SeatRepository {
    fun getSeatsByPerformanceIdAndScheduleId(
        performanceId: Long,
        scheduleId: Long,
    ): List<Seat>

    fun findAvailableSeatsWithLock(
        performanceId: Long,
        scheduleId: Long,
        seatIds: List<Long>,
    ): List<SeatHolding>

    fun updateSeatStatus(
        seatIds: List<Long>,
        status: SeatStatus,
    )
}
