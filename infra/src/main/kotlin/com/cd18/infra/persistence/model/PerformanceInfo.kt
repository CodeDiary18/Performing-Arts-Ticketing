package com.cd18.infra.persistence.model

import com.cd18.infra.persistence.config.model.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

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
    val startDate: String,
    @Column(name = "end_date")
    val endDate: String,
) : BaseTimeEntity()
