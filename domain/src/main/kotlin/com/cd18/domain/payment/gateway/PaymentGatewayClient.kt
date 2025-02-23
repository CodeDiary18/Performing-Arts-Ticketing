package com.cd18.domain.payment.gateway

import com.cd18.domain.payment.dto.PaymentRequest

interface PaymentGatewayClient {
    fun requestPayment(paymentRequest: PaymentRequest)
}
