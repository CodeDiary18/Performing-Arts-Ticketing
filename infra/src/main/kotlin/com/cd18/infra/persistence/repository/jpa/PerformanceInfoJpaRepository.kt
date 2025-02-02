package com.cd18.infra.persistence.repository.jpa

import com.cd18.infra.persistence.model.PerformanceInfo
import org.springframework.data.jpa.repository.JpaRepository

interface PerformanceInfoJpaRepository : JpaRepository<PerformanceInfo, Long>
