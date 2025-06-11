package com.cd18.infra.mail.template

import com.cd18.domain.ticketing.dto.ReserveInfoDto

class ReserveConfirmTemplate(
    private val reserveInfo: ReserveInfoDto,
) : MailTemplate {
    override fun getSubject() = "${reserveInfo.performanceName} 공연 예약 정보입니다."

    override fun getBody(): String {
        return """
            <h1>${reserveInfo.performanceName} 공연 예약 정보입니다.</h1>
            <p>예약하신 공연의 일정은 ${reserveInfo.performanceScheduleDate} ${reserveInfo.performanceScheduleTime} 입니다.</p>
            <p>예약하신 좌석은 ${reserveInfo.seatItems.joinToString(", ") { it.seatName }} 입니다.</p>
            """.trimIndent()
    }
}
