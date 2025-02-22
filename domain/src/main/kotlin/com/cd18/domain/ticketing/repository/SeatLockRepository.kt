package com.cd18.domain.ticketing.repository

import com.cd18.domain.ticketing.model.SeatLockGroup
import java.util.UUID

interface SeatLockRepository {
    fun saveAllFromGroup(seatLockGroup: SeatLockGroup)

    fun getSeatLockGroupByLockGroupId(
        userId: Long,
        lockGroupId: UUID,
    ): SeatLockGroup

    fun deleteBySeatLockGroup(
        userId: Long,
        lockGroupId: UUID,
    ): Long
}
