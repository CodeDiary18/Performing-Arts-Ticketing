package com.cd18.infra.persistence.model

import com.cd18.infra.persistence.config.model.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "performance_schedule")
class PerformanceSchedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "perf_id")
    var performanceInfoId: Long,
    @Column(name = "perf_time")
    var startTime: LocalDateTime,
) : BaseTimeEntity()
