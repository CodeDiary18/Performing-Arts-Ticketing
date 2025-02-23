package com.cd18.infra.persistence.repository

import com.cd18.common.exception.BaseException
import com.cd18.common.http.response.code.BaseErrorCode
import com.cd18.domain.ticketing.model.SeatLockGroup
import com.cd18.domain.ticketing.repository.SeatLockRepository
import com.cd18.infra.persistence.model.QSeatLock.seatLock
import com.cd18.infra.persistence.model.SeatLock
import com.cd18.infra.persistence.repository.extensions.filterActive
import com.cd18.infra.persistence.repository.extensions.filterLockGroup
import com.cd18.infra.persistence.repository.jpa.SeatLockJpaRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.util.UUID

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

    override fun getSeatLockGroupByLockGroupId(
        userId: Long,
        lockGroupId: UUID,
    ): SeatLockGroup {
        val seatIds: List<Long> =
            queryFactory.select(seatLock.seatId)
                .from(seatLock)
                .where(seatLock.userId.eq(userId))
                .filterLockGroup(lockGroupId = lockGroupId, userId = userId)
                .filterActive()
                .fetch()

        if (seatIds.isEmpty()) {
            throw BaseException(BaseErrorCode.NOT_FOUND)
        }

        return SeatLockGroup(
            lockGroupId = lockGroupId,
            userId = userId,
            seatIds = seatIds,
        )
    }

    override fun deleteBySeatLockGroup(
        userId: Long,
        lockGroupId: UUID,
    ): Long {
        return queryFactory.delete(seatLock)
            .filterLockGroup(lockGroupId = lockGroupId, userId = userId)
            .execute()
    }
}
