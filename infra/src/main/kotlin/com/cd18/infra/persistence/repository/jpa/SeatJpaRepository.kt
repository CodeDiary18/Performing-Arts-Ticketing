package com.cd18.infra.persistence.repository.jpa

import com.cd18.infra.persistence.model.Seat
import org.springframework.data.jpa.repository.JpaRepository

interface SeatJpaRepository : JpaRepository<Seat, Long> {
    fun existsByPerformanceInfoIdAndPerformanceScheduleId(
        performanceInfoId: Long,
        performanceScheduleId: Long,
    ): Boolean
}
