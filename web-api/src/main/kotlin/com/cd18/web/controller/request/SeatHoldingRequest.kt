package com.cd18.web.controller.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Schema(description = "좌석 홀딩 요청 정보")
data class SeatHoldingRequest(
    @Schema(description = "홀딩할 좌석 ID 리스트", required = true, example = "[1, 2, 3]")
    @field:NotNull(message = "좌석 ID 리스트는 필수입니다.")
    @field:Size(min = 1, message = "최소 한 개의 좌석 ID를 선택해야 합니다.")
    val seatIds: List<Long>,
)
