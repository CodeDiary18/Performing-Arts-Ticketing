package com.cd18.web.controller.response

import com.cd18.domain.performance.model.PerformanceSchedule
import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalTime

@Schema(description = "공연 스케줄 정보 목록 응답")
data class PerformanceScheduleListResponse(
    @Schema(description = "공연 id", required = true)
    val performanceId: Long,
    @Schema(description = "공연 스케줄 목록", required = true)
    val performanceScheduleList: List<PerformanceScheduleInfo>,
) {
    @Schema(description = "공연 스케줄 DTO")
    data class PerformanceScheduleInfo(
        @Schema(description = "공연 스케줄 id", example = "1")
        val scheduleId: Long,
        @Schema(description = "공연 스케줄 날짜", example = "2025-03-01")
        val scheduleDate: LocalDate,
        @Schema(description = "공연 스케줄 시간", example = "14:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        val scheduleTime: LocalTime,
    )

    companion object {
        fun of(
            performanceId: Long,
            performanceScheduleList: List<PerformanceSchedule>,
        ) = PerformanceScheduleListResponse(
            performanceId = performanceId,
            performanceScheduleList =
                performanceScheduleList.map {
                    PerformanceScheduleInfo(
                        scheduleId = it.performanceScheduleId,
                        scheduleDate = it.scheduleDate,
                        scheduleTime = it.scheduleTime,
                    )
                },
        )
    }
}
