package com.cd18.infra.persistence.repository

import com.cd18.domain.ticketing.model.SeatLockGroup
import com.cd18.domain.ticketing.repository.SeatLockRepository
import com.cd18.infra.persistence.model.SeatLock
import com.cd18.infra.persistence.repository.jpa.SeatLockJpaRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class SeatLockRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
    private val seatLockJpaRepository: SeatLockJpaRepository,
) : SeatLockRepository {
    override fun saveAllFromGroup(seatLockGroup: SeatLockGroup) {
        val seatLocks =
            seatLockGroup.seatIds.map {
                SeatLock(
                    lockGroupId = seatLockGroup.lockGroupId,
                    userId = seatLockGroup.userId,
                    scheduleId = seatLockGroup.scheduleId,
                    seatId = it,
                    expireTime = seatLockGroup.expireTime,
                )
            }
        seatLockJpaRepository.saveAll(seatLocks)
    }
}
