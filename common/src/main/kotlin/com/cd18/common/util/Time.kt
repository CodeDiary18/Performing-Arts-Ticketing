package com.cd18.common.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun getCurrentTime() = LocalDateTime.now()

fun getTodayDate() = LocalDate.now()

fun LocalDate.getStartOfDay() = LocalDateTime.of(this, LocalTime.MIN)

fun LocalDate.getEndOfDay() = LocalDateTime.of(this, LocalTime.MAX)

fun LocalDateTime.getStartOfHour(): LocalDateTime = this.withMinute(0).withSecond(0).withNano(0)

fun LocalDateTime.getEndOfHour(): LocalDateTime =
    this.withMinute(
        LocalTime.MAX.minute,
    ).withSecond(LocalTime.MAX.second).withNano(LocalTime.MAX.nano)

fun LocalDate.getLastDayOfMonth(): Int = LocalDate.of(year, month, 1).lengthOfMonth()

fun LocalDateTime.getDateAndHour(): Pair<LocalDate, Int> = toLocalDate() to hour

fun LocalDateTime.formattedTime(format: String = "yyyy-MM-dd'T'HH:mm:ss"): String = DateTimeFormatter.ofPattern(format).format(this)

fun LocalDate.formattedDate(format: String = "yyyy-MM-dd"): String = DateTimeFormatter.ofPattern(format).format(this)

fun getLocalDateTime(
    date: LocalDate,
    hour: Int,
): LocalDateTime = date.atTime(hour, 0, 0)
