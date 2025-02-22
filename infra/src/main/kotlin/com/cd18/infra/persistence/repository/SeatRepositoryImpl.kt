package com.cd18.infra.persistence.repository

import com.cd18.common.exception.BaseException
import com.cd18.domain.performance.enums.PerformanceInfoErrorCode
import com.cd18.domain.ticketing.enums.SeatStatus
import com.cd18.domain.ticketing.model.Seat
import com.cd18.domain.ticketing.model.SeatHolding
import com.cd18.domain.ticketing.repository.SeatRepository
import com.cd18.infra.persistence.model.QSeat.seat
import com.cd18.infra.persistence.repository.jpa.SeatJpaRepository
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.LockModeType
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class SeatRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
    private val seatJpaRepository: SeatJpaRepository,
) : SeatRepository {
    override fun getSeatsByPerformanceIdAndScheduleId(
        performanceId: Long,
        scheduleId: Long,
    ): List<Seat> {
        seatJpaRepository.existsByPerformanceInfoIdAndPerformanceScheduleId(
            performanceInfoId = performanceId,
            performanceScheduleId = scheduleId,
        ).takeIf { it } ?: throw BaseException(PerformanceInfoErrorCode.NOT_FOUND)

        return queryFactory.select(
            Projections.constructor(
                Seat::class.java,
                seat.id,
                seat.seatName,
                seat.posX,
                seat.posY,
                seat.status,
            ),
        ).from(seat)
            .where(
                seat.performanceInfoId.eq(performanceId),
                seat.performanceScheduleId.eq(scheduleId),
            )
            .orderBy(seat.id.asc())
            .fetch()
    }

    override fun findAvailableSeatsWithLock(
        performanceId: Long,
        scheduleId: Long,
        seatIds: List<Long>,
    ): List<SeatHolding> {
        return queryFactory.select(
            Projections.constructor(
                SeatHolding::class.java,
                seat.id,
                seat.seatName,
                seat.status,
            ),
        ).from(seat)
            .where(
                seat.status.eq(SeatStatus.AVAILABLE),
                seat.performanceInfoId.eq(performanceId),
                seat.performanceScheduleId.eq(scheduleId),
                seat.id.`in`(seatIds),
            )
            .setLockMode(LockModeType.PESSIMISTIC_WRITE)
            .fetch()
    }

    override fun updateSeatStatus(
        seatIds: List<Long>,
        status: SeatStatus,
    ): Long {
        return queryFactory.update(seat)
            .set(seat.status, status)
            .set(seat.updatedAt, LocalDateTime.now())
            .where(seat.id.`in`(seatIds))
            .execute()
    }
}
