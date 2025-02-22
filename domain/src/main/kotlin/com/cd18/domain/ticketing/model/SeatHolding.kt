package com.cd18.domain.ticketing.model

import com.cd18.domain.ticketing.enums.SeatStatus

class SeatHolding(
    val seatId: Long,
    val seatName: String,
    private val status: SeatStatus,
)
