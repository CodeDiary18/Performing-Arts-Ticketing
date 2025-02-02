package com.cd18.infra.persistence.model

import com.cd18.infra.persistence.config.model.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "performance_info")
class PerformanceInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "perf_name")
    val name: String,
    @Column(name = "perf_desc")
    val description: String,
    @Column(name = "perf_venue")
    val venue: String,
    @Column(name = "start_date")
    val startDate: LocalDate,
    @Column(name = "end_date")
    val endDate: LocalDate,
//    TODO: Add ticketing start and end date
//    @Column(name = "ticketing_start_date")
//    val ticketingStartDate: LocalDateTime,
//    @Column(name = "ticketing_end_date")
//    val ticketingEndDate: LocalDateTime,
) : BaseTimeEntity()
