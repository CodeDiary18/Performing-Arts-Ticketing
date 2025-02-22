package com.cd18.domain.ticketing.repository

import com.cd18.domain.ticketing.model.SeatLockGroup

interface SeatLockRepository {
    fun saveAllFromGroup(seatLockGroup: SeatLockGroup)
}
