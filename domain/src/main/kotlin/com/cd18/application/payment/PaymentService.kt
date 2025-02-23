package com.cd18.application.payment

import com.cd18.domain.payment.dto.PaymentRequest
import com.cd18.domain.payment.enums.PaymentStatus

interface PaymentService {
    fun processPayment(paymentRequest: PaymentRequest)

    fun checkPaymentStatus(transactionId: String): PaymentStatus
}
