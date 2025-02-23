package com.cd18.application.payment.impl

import com.cd18.application.payment.PaymentService
import com.cd18.domain.payment.dto.PaymentRequest
import com.cd18.domain.payment.enums.PaymentStatus
import com.cd18.domain.payment.gateway.PaymentGatewayFactory
import org.springframework.stereotype.Service

@Service
class PaymentServiceImpl(
    private val paymentGatewayFactory: PaymentGatewayFactory,
) : PaymentService {
    override fun processPayment(paymentRequest: PaymentRequest) {
        // TODO : Implement payment process
        val pgClient = paymentGatewayFactory.getClient(paymentRequest.pgType)
    }

    override fun checkPaymentStatus(transactionId: String): PaymentStatus {
        // TODO : Implement payment status check
        return PaymentStatus.SUCCESS
    }
}
