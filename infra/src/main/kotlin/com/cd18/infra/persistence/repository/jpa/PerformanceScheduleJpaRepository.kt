package com.cd18.infra.persistence.repository.jpa

import com.cd18.infra.persistence.model.PerformanceSchedule
import org.springframework.data.jpa.repository.JpaRepository

interface PerformanceScheduleJpaRepository : JpaRepository<PerformanceSchedule, Long>
