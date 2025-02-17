package com.cd18.infra.persistence.repository.jpa

import com.cd18.infra.persistence.model.PerformancePrice
import org.springframework.data.jpa.repository.JpaRepository

interface PerformancePriceJpaRepository : JpaRepository<PerformancePrice, Long>
