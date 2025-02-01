package com.cd18.infra.persistence.model

import com.cd18.infra.persistence.config.model.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "history_performance_discount")
class HistoryPerformanceDiscount(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "perf_id")
    val performanceInfoId: Long,
    @Column(name = "perf_dc_id")
    val performanceDiscountId: Long,
    @Column(name = "perf_dc_price")
    val discountPrice: Int,
) : BaseTimeEntity()
