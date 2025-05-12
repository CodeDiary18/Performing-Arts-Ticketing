package com.cd18.domain.performance.dto

import com.cd18.domain.performance.model.PerformancePrice
import java.time.LocalDate

data class PerformanceInfoDetailDto(
    val id: Long,
    val performanceName: String,
    val performanceDescription: String,
    val performanceVenue: String,
    override val performanceOriginPrice: Int,
    override val performanceDiscountPrice: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
) : PerformancePrice(
        performanceOriginPrice = performanceOriginPrice,
        performanceDiscountPrice = performanceDiscountPrice,
    )
