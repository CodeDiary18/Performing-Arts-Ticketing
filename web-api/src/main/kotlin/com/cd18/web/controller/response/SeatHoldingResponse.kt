package com.cd18.web.controller.response

import com.cd18.domain.ticketing.model.SeatLockGroup
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

@Schema(description = "공연 좌석 홀딩 응답 정보")
data class SeatHoldingResponse(
    @Schema(description = "홀딩 group id", example = "787d9f06-2731-4731-91b8-12f2b867f061")
    val holdingGroupId: UUID,
    @Schema(description = "홀딩 유효 시간", example = "2025-01-01T01:10:00")
    val expireTime: LocalDateTime,
) {
    companion object {
        fun of(seatLockGroup: SeatLockGroup) =
            SeatHoldingResponse(
                holdingGroupId = seatLockGroup.lockGroupId,
                expireTime = seatLockGroup.expireTime,
            )
    }
}
