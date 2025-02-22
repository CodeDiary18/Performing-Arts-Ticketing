package com.cd18.infra.persistence.model

import com.cd18.infra.persistence.config.model.BaseCreatedTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "seat_lock")
class SeatLock(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "lock_group_id", nullable = false, updatable = false)
    val lockGroupId: UUID,
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "schedule_id")
    val scheduleId: Long,
    @Column(name = "seat_id")
    val seatId: Long,
    @Column(name = "expire_time", nullable = false)
    val expireTime: LocalDateTime,
) : BaseCreatedTimeEntity()
