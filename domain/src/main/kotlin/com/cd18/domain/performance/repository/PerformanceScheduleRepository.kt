package com.cd18.domain.performance.repository

import com.cd18.domain.performance.model.PerformanceSchedule

interface PerformanceScheduleRepository {
    fun getScheduleInfoByPerformanceId(id: Long): List<PerformanceSchedule>
}
