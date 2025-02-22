package com.cd18.infra.persistence.repository.extensions

import com.cd18.common.util.getCurrentTime
import com.cd18.infra.persistence.model.QSeatLock.seatLock
import com.querydsl.jpa.impl.JPADeleteClause
import com.querydsl.jpa.impl.JPAQuery
import java.util.UUID

fun <T> JPAQuery<T>.filterActive(): JPAQuery<T> {
    return this.where(
        seatLock.expireTime.gt(getCurrentTime()),
    )
}

fun <T> JPAQuery<T>.filterLockGroup(
    lockGroupId: UUID,
    userId: Long? = null,
): JPAQuery<T> {
    return this.where(
        seatLock.lockGroupId.eq(lockGroupId),
        userId?.let { seatLock.userId.eq(it) },
    )
}

fun JPADeleteClause.filterLockGroup(
    lockGroupId: UUID,
    userId: Long? = null,
): JPADeleteClause {
    return this.where(
        seatLock.lockGroupId.eq(lockGroupId),
        userId?.let { seatLock.userId.eq(it) },
    )
}
