package com.cd18.infra.persistence.model

import com.cd18.domain.ticketing.enums.SeatStatus
import com.cd18.infra.persistence.config.model.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "seat")
class Seat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "perf_id")
    val performanceInfoId: Long,
    @Column(name = "perf_schedule_id")
    val performanceScheduleId: Long,
    @Column(name = "seat_name")
    val seatName: String,
    @Column(name = "pos_x")
    val posX: Int,
    @Column(name = "pos_y")
    val posY: Int,
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val status: SeatStatus = SeatStatus.AVAILABLE,
) : BaseTimeEntity()
