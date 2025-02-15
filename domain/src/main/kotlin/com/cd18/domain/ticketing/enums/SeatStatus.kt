package com.cd18.domain.ticketing.enums

enum class SeatStatus {
    AVAILABLE, // 선택 가능 (예약 가능)
    PENDING, // 예약 진행 중
    RESERVED, // 예약 완료
    BLOCKED, // 관리자에서 제외한 좌석 (사용 불가)
}
