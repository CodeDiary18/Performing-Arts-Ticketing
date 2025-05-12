package com.cd18.web.controller.response

import com.cd18.domain.performance.dto.PerformanceInfoDetailDto
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "공연 정보 상세 응답")
data class PerformanceInfoDetailResponse(
    @Schema(description = "공연 ID", example = "1")
    val performanceId: Long,
    @Schema(description = "공연명", example = "레미제라블")
    val performanceName: String,
    @Schema(description = "공연 설명", example = "뮤지컬 레미제라블")
    val performanceDescription: String,
    @Schema(description = "공연 장소", example = "블루스퀘어 신한카드홀")
    val performanceVenue: String,
    @Schema(description = "공연 티켓 가격", example = "50000")
    val performanceOriginPrice: Int,
    @Schema(description = "공연 할인 가격", example = "45000")
    val performanceDiscountPrice: Int,
    @Schema(description = "공연 할인율", example = "10")
    val performanceDiscountRate: Int,
    @Schema(description = "공연 시작일", example = "2025-03-01")
    val startDate: String,
    @Schema(description = "공연 종료일", example = "2025-05-05")
    val endDate: String,
) {
    companion object {
        fun of(performanceInfoDetailDto: PerformanceInfoDetailDto) =
            PerformanceInfoDetailResponse(
                performanceId = performanceInfoDetailDto.id,
                performanceName = performanceInfoDetailDto.performanceName,
                performanceDescription = performanceInfoDetailDto.performanceDescription,
                performanceVenue = performanceInfoDetailDto.performanceVenue,
                performanceOriginPrice = performanceInfoDetailDto.performanceOriginPrice,
                performanceDiscountPrice = performanceInfoDetailDto.performanceDiscountPrice,
                performanceDiscountRate = performanceInfoDetailDto.performanceDiscountRate,
                startDate = performanceInfoDetailDto.startDate.toString(),
                endDate = performanceInfoDetailDto.endDate.toString(),
            )
    }
}
