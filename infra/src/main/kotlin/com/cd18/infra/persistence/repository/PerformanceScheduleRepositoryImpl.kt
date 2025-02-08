package com.cd18.infra.persistence.repository

import com.cd18.domain.performance.model.PerformanceSchedule
import com.cd18.domain.performance.repository.PerformanceScheduleRepository
import com.cd18.infra.persistence.model.QPerformanceSchedule.performanceSchedule
import com.cd18.infra.persistence.repository.jpa.PerformanceScheduleJpaRepository
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class PerformanceScheduleRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
    private val performanceScheduleJpaRepository: PerformanceScheduleJpaRepository,
) : PerformanceScheduleRepository {
    override fun getScheduleInfoByPerformanceId(id: Long): List<PerformanceSchedule> {
        return queryFactory.select(
            Projections.constructor(
                PerformanceSchedule::class.java,
                performanceSchedule.id,
                performanceSchedule.startTime,
            ),
        ).from(performanceSchedule)
            .where(
                performanceSchedule.performanceInfoId.eq(id),
            )
            .orderBy(performanceSchedule.startTime.asc())
            .fetch()
    }
}
