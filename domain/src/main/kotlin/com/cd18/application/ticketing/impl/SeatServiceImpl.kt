package com.cd18.application.ticketing.impl

import com.cd18.application.ticketing.SeatService
import com.cd18.common.exception.BaseException
import com.cd18.domain.ticketing.enums.SeatStatus
import com.cd18.domain.ticketing.enums.TicketingErrorCode
import com.cd18.domain.ticketing.model.Seat
import com.cd18.domain.ticketing.model.SeatLockGroup
import com.cd18.domain.ticketing.repository.SeatLockRepository
import com.cd18.domain.ticketing.repository.SeatRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class SeatServiceImpl(
    private val seatRepository: SeatRepository,
    private val seatLockRepository: SeatLockRepository,
) : SeatService {
    private val logger = KotlinLogging.logger {}

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

    @Transactional
    override fun holdingSeats(
        userId: Long,
        performanceId: Long,
        scheduleId: Long,
        seatIds: List<Long>,
    ): Result<SeatLockGroup> {
        logger.debug { "performanceId: $performanceId, scheduleId: $scheduleId, seatIds: $seatIds" }

//        TODO : check user has already holding seats

        val availableSeats =
            seatRepository.findAvailableSeatsWithLock(
                performanceId = performanceId,
                scheduleId = scheduleId,
                seatIds = seatIds,
            )

        if (availableSeats.size != seatIds.size) {
            throw BaseException(TicketingErrorCode.ALREADY_SEAT_HOLD)
        }

        val seatLockGroup =
            SeatLockGroup.create(
                userId = userId,
                scheduleId = scheduleId,
                seatIds = seatIds,
            )

        seatLockRepository.saveAllFromGroup(seatLockGroup)
        seatRepository.updateSeatStatus(
            seatIds = seatIds,
            status = SeatStatus.HOLDING,
        )

        return Result.success(seatLockGroup)
    }

    @Transactional
    override fun cancelSeatLockGroup(
        userId: Long,
        lockGroupId: UUID,
    ): Result<Unit> {
        val seatLockGroup = seatLockRepository.getSeatLockGroupByLockGroupId(userId = userId, lockGroupId = lockGroupId)

        seatRepository.updateSeatStatus(
            seatIds = seatLockGroup.seatIds,
            status = SeatStatus.AVAILABLE,
        )
        seatLockRepository.deleteBySeatLockGroup(
            userId = userId,
            lockGroupId = lockGroupId,
        )

        return Result.success(Unit)
    }
}
