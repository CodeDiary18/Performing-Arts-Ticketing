package com.cd18.domain.ticketing.repository

import com.cd18.domain.ticketing.model.Seat

interface SeatRepository {
    fun getSeatsByPerformanceIdAndScheduleId(
        performanceId: Long,
        scheduleId: Long,
    ): List<Seat>
}
