package com.cd18.domain.payment.gateway.pg

import com.cd18.domain.payment.dto.PaymentRequest
import com.cd18.domain.payment.gateway.PaymentGatewayClient
import org.springframework.stereotype.Component

@Component
class TossPayClient : PaymentGatewayClient {
    override fun requestPayment(paymentRequest: PaymentRequest) {
        TODO("Not yet implemented")
    }
}
