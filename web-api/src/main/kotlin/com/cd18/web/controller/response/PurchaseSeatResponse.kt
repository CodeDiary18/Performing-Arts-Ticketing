package com.cd18.web.controller.response

import com.cd18.domain.ticketing.model.Ticket
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "공연 좌석 구매 후 응답 정보")
data class PurchaseSeatResponse(
    @Schema(description = "발급된 티켓 번호", example = "T210101ABCD")
    val ticketNo: String,
) {
    companion object {
        fun of(ticket: Ticket) = PurchaseSeatResponse(ticketNo = ticket.ticketNo)
    }
}
