package com.cd18.application.ticketing.impl

import com.cd18.application.ticketing.SeatService
import com.cd18.domain.ticketing.model.Seat
import com.cd18.domain.ticketing.repository.SeatRepository
import org.springframework.stereotype.Service

@Service
class SeatServiceImpl(
    private val seatRepository: SeatRepository,
) : SeatService {
    override fun getSeatsBySchedule(
        performanceId: Long,
        scheduleId: Long,
    ): Result<List<Seat>> =
        runCatching {
            seatRepository.getSeatsByPerformanceIdAndScheduleId(
                performanceId = performanceId,
                scheduleId = scheduleId,
            )
        }
}
