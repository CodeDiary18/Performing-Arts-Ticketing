package com.cd18.infra.persistence.repository.jpa

import com.cd18.infra.persistence.model.SeatLock
import org.springframework.data.jpa.repository.JpaRepository

interface SeatLockJpaRepository : JpaRepository<SeatLock, Long>
