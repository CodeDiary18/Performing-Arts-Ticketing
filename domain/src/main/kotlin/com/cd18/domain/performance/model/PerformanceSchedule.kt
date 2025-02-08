package com.cd18.domain.performance.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class PerformanceSchedule(
    val performanceScheduleId: Long,
    private val scheduleDateTime: LocalDateTime,
) {
    val scheduleDate: LocalDate
        get() = scheduleDateTime.toLocalDate()
    val scheduleTime: LocalTime
        get() = scheduleDateTime.toLocalTime()
}
