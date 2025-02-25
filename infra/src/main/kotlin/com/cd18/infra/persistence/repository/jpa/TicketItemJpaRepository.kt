package com.cd18.infra.persistence.repository.jpa

import com.cd18.infra.persistence.model.TicketItem
import org.springframework.data.jpa.repository.JpaRepository

interface TicketItemJpaRepository : JpaRepository<TicketItem, Long>
