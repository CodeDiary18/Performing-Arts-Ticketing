package com.cd18.domain.ticketing.enums

import com.cd18.common.http.response.code.ErrorCode
import org.springframework.http.HttpStatus

enum class TicketingErrorCode(
    override val message: String,
    override val status: HttpStatus,
) : ErrorCode {
    ALREADY_SEAT_HOLD("선택한 좌석 중 일부가 이미 예약되었습니다.", HttpStatus.BAD_REQUEST),
    ;

    override val prefix: String = "TICKETING"
}
