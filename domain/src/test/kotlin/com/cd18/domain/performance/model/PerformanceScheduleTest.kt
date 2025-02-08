package com.cd18.domain.performance.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime

class PerformanceScheduleTest {
    @Test
    fun `should create PerformanceSchedule with valid values`() {
        val performanceSchedule =
            PerformanceSchedule(
                performanceScheduleId = 1,
                scheduleDateTime = java.time.LocalDateTime.of(2025, 1, 1, 10, 15),
            )

        assertEquals(1, performanceSchedule.performanceScheduleId)
        assertEquals(LocalDate.of(2025, 1, 1), performanceSchedule.scheduleDate)
        assertEquals(LocalTime.of(10, 15), performanceSchedule.scheduleTime)
    }
}
