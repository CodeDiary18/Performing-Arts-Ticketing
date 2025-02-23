package com.cd18.web.controller

import com.cd18.application.ticketing.SeatService
import com.cd18.common.http.annotation.CurrentUser
import com.cd18.common.http.response.ApiResponse
import com.cd18.web.controller.request.SeatHoldingRequest
import com.cd18.web.controller.response.SeatHoldingResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/ticketing")
@Tag(name = "공연 티켓팅", description = "공연 티켓팅 APIs")
class TicketingController(
    private val seatService: SeatService,
) {
    @PostMapping("/{performanceId}/{scheduleId}/seats/hold")
    @Operation(
        summary = "좌석 홀딩",
        description = "공연 일정에 대해 선택된 좌석을 일정 시간 동안 홀딩합니다.",
    )
    fun holdingSeats(
        @CurrentUser memberId: Long,
        @Parameter(description = "공연 ID", required = true, example = "1")
        @PathVariable performanceId: Long,
        @Parameter(description = "공연 스케줄 ID", required = true, example = "1")
        @PathVariable scheduleId: Long,
        @RequestBody seatHoldingRequest: SeatHoldingRequest,
    ): ApiResponse<SeatHoldingResponse> {
        val seatLockGroup =
            seatService.holdingSeats(
                userId = memberId,
                performanceId = performanceId,
                scheduleId = scheduleId,
                seatIds = seatHoldingRequest.seatIds,
            ).getOrThrow()
        return ApiResponse(result = SeatHoldingResponse.of(seatLockGroup))
    }

    @DeleteMapping("/{performanceId}/{scheduleId}/seats/hold/{holdingGroupId}")
    @Operation(
        summary = "좌석 홀딩 해제",
        description = "공연 일정에 대해 홀딩된 좌석을 해제합니다.",
    )
    fun cancelHoldingSeats(
        @CurrentUser memberId: Long,
        @Parameter(description = "공연 ID", required = true, example = "1")
        @PathVariable performanceId: Long,
        @Parameter(description = "공연 스케줄 ID", required = true, example = "1")
        @PathVariable scheduleId: Long,
        @Parameter(description = "홀딩 그룹 ID", required = true, example = "abcd-1234-efgh-5678")
        @PathVariable holdingGroupId: UUID,
    ): ApiResponse<Unit> {
        seatService.cancelSeatLockGroup(
            userId = memberId,
            lockGroupId = holdingGroupId,
        ).getOrThrow()
        return ApiResponse()
    }
}
