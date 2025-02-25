package com.cd18.domain.ticketing.repository

import com.cd18.domain.ticketing.model.Ticket as TicketDomain

interface TicketRepository {
    fun save(ticketDomain: TicketDomain): Long
}
