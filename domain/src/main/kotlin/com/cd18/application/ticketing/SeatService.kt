package com.cd18.application.ticketing

import com.cd18.domain.ticketing.model.Seat

interface SeatService {
    fun getSeatsBySchedule(
        performanceId: Long,
        scheduleId: Long,
    ): Result<List<Seat>>
}
