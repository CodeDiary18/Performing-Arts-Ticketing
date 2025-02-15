package com.cd18.web.controller.response

import com.cd18.domain.ticketing.model.Seat
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "공연 스케줄 정보 목록 응답")
data class SeatListResponse(
    @Schema(description = "공연 id", required = true)
    val performanceId: Long,
    @Schema(description = "공연 스케줄 id", example = "1")
    val scheduleId: Long,
    @Schema(description = "공연 좌석 정보 목록", required = true)
    val seatList: List<SeatInfo>,
) {
    @Schema(description = "공연 좌석 DTO")
    data class SeatInfo(
        @Schema(description = "좌석 id", example = "1")
        val seatId: Long,
        @Schema(description = "좌석 이름", example = "A1")
        val seatName: String,
        @Schema(description = "좌석 x 좌표", example = "1")
        val posX: Int,
        @Schema(description = "좌석 y 좌표", example = "1")
        val posY: Int,
        @Schema(description = "좌석 예매 가능 상태(true: 활성, false: 비활성)", example = "true")
        val isAvailable: Boolean,
    )

    companion object {
        fun of(
            performanceId: Long,
            scheduleId: Long,
            seatInfoList: List<Seat>,
        ) = SeatListResponse(
            performanceId = performanceId,
            scheduleId = scheduleId,
            seatList =
                seatInfoList.map {
                    SeatInfo(
                        seatId = it.seatId,
                        seatName = it.seatName,
                        posX = it.posX,
                        posY = it.posY,
                        isAvailable = it.isAvailable,
                    )
                },
        )
    }
}
