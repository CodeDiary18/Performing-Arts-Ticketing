package com.cd18.domain.payment.enums

enum class PaymentStatus {
    PENDING, // 결제 진행 중
    SUCCESS, // 결제 성공
    FAILED, // 결제 실패
    CANCELED, // 결제 취소됨
    TIMEOUT, // 결제 시간 초과
}
