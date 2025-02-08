package com.cd18.application.performance

import com.cd18.domain.common.page.PageParam
import com.cd18.domain.performance.dto.PerformanceInfoDetailDto
import com.cd18.domain.performance.dto.PerformanceInfoDto
import com.cd18.domain.performance.model.PerformanceSchedule

interface PerformanceInfoService {
    fun getList(pageParam: PageParam): Result<List<PerformanceInfoDto>>

    fun getById(id: Long): Result<PerformanceInfoDetailDto>

    fun changeDiscountPrice(
        id: Long,
        discountPrice: Int,
    ): Result<Unit>

    fun getScheduleInfoById(id: Long): Result<List<PerformanceSchedule>>
}
