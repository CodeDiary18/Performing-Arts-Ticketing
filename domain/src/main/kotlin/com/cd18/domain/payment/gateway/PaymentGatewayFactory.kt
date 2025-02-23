package com.cd18.domain.payment.gateway

import com.cd18.common.exception.BaseException
import com.cd18.domain.payment.enums.PaymentErrorCode
import com.cd18.domain.payment.enums.PaymentGateway
import com.cd18.domain.payment.gateway.pg.TossPayClient
import org.springframework.stereotype.Component

@Component
class PaymentGatewayFactory(
    private val tossPayClient: TossPayClient,
) {
    fun getClient(pgType: PaymentGateway): PaymentGatewayClient {
        return when (pgType) {
            PaymentGateway.TOSS -> tossPayClient
            else -> throw BaseException(PaymentErrorCode.NOT_SUPPORTED_PG)
        }
    }
}
