package com.cd18.domain.performance.repository

import com.cd18.domain.common.page.PageParam
import com.cd18.domain.performance.dto.PerformanceInfoDetailDto
import com.cd18.domain.performance.dto.PerformanceInfoDto
import com.cd18.domain.performance.dto.PerformancePriceDto

interface PerformanceInfoRepository {
    fun isExistById(id: Long): Boolean

    fun getList(pageParam: PageParam): List<PerformanceInfoDto>

    fun getById(id: Long): PerformanceInfoDetailDto

    fun getPriceInfoByPerformanceId(performanceId: Long): PerformancePriceDto

    fun updateDiscountPrice(
        performanceId: Long,
        performanceDiscountId: Long,
        discountPrice: Int,
    )
}
