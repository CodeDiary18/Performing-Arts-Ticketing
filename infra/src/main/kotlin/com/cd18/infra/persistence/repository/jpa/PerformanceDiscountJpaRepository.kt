package com.cd18.infra.persistence.repository.jpa

import com.cd18.infra.persistence.model.PerformanceDiscount
import org.springframework.data.jpa.repository.JpaRepository

interface PerformanceDiscountJpaRepository : JpaRepository<PerformanceDiscount, Long>
