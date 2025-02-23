package com.cd18.domain.payment.enums

import com.cd18.common.http.response.code.ErrorCode
import org.springframework.http.HttpStatus

enum class PaymentErrorCode(
    override val message: String,
    override val status: HttpStatus,
) : ErrorCode {
    INVALID_PAYMENT("유효하지 않은 결제입니다.", HttpStatus.BAD_REQUEST),
    NOT_SUPPORTED_PG("지원하지 않는 결제사입니다.", HttpStatus.BAD_REQUEST),
    ;

    override val prefix: String = "PAYMENT"
}
