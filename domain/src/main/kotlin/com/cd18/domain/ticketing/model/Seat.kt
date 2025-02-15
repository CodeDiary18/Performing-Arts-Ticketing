package com.cd18.domain.ticketing.model

import com.cd18.domain.ticketing.enums.SeatStatus

class Seat(
    val seatId: Long,
    val seatName: String,
    val posX: Int,
    val posY: Int,
    val status: SeatStatus,
)
