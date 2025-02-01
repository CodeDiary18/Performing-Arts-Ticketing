package com.cd18.infra.persistence.model

import com.cd18.infra.persistence.config.model.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "performance_hourly_view_stat")
class PerformanceHourlyViewStat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "statistic_date")
    val statisticDate: LocalDate,
    @Column(name = "statistic_hour")
    val statisticHour: Int,
    @Column(name = "perf_id")
    val performanceInfoId: Long,
    @Column(name = "view_count", nullable = false)
    val viewCount: Long,
) : BaseTimeEntity() {
    init {
        require(statisticHour in 0..23) { "statisticHour must be between 0 and 23" }
    }
}
