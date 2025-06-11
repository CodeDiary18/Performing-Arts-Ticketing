package com.cd18.domain.ticketing.dto

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class ReserveSeatItemDto(
    val seatId: Long,
    val seatName: String,
)

data class ReserveInfoDto(
    val performanceName: String,
    val performanceSchedule: LocalDateTime,
    val seatItems: List<ReserveSeatItemDto>,
) {
    val performanceScheduleDate: LocalDate
        get() = performanceSchedule.toLocalDate()
    val performanceScheduleTime: LocalTime
        get() = performanceSchedule.toLocalTime()
}
