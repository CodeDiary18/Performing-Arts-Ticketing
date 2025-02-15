package com.cd18.infra.persistence.repository

import com.cd18.common.exception.BaseException
import com.cd18.domain.performance.enums.PerformanceInfoErrorCode
import com.cd18.domain.ticketing.model.Seat
import com.cd18.domain.ticketing.repository.SeatRepository
import com.cd18.infra.persistence.model.QSeat.seat
import com.cd18.infra.persistence.repository.jpa.SeatJpaRepository
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

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
}
