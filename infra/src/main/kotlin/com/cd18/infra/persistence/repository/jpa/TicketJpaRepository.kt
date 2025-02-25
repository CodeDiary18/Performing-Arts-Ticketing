package com.cd18.infra.persistence.repository.jpa

import com.cd18.infra.persistence.model.Ticket
import org.springframework.data.jpa.repository.JpaRepository

interface TicketJpaRepository : JpaRepository<Ticket, Long>
