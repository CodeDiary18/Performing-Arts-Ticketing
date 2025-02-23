package com.cd18.domain.payment.dto

import com.cd18.domain.payment.enums.PaymentGateway

data class PaymentRequest(
    val pgType: PaymentGateway,
    val userId: Long,
    val amount: Double,
    // TODO: Add more fields
)
