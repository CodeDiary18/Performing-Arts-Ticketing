package com.cd18.infra.persistence.repository

import com.cd18.domain.ticketing.repository.TicketRepository
import com.cd18.infra.persistence.model.Ticket
import com.cd18.infra.persistence.repository.jpa.TicketItemJpaRepository
import com.cd18.infra.persistence.repository.jpa.TicketJpaRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import com.cd18.domain.ticketing.model.Ticket as TicketDomain

@Repository
class TicketRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
    private val ticketJpaRepository: TicketJpaRepository,
    private val ticketItemJpaRepository: TicketItemJpaRepository,
) : TicketRepository {
    override fun save(ticketDomain: TicketDomain): Long {
        val ticketEntity = Ticket.create(ticketDomain)
        val ticket = ticketJpaRepository.save(ticketEntity)
        return ticket.id
    }
}
