package com.cd18.domain.payment.enums

import com.cd18.common.http.response.code.SuccessCode

enum class PaymentSuccessCode(
    override val message: String,
) : SuccessCode {
    OK("결제가 성공적으로 완료되었습니다."),
    ;

    override val prefix: String = "PAYMENT"
}
