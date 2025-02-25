package com.cd18.infra.persistence.model

import com.cd18.domain.ticketing.enums.TicketStatus
import com.cd18.infra.persistence.config.model.BaseTimeEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime
import com.cd18.domain.ticketing.model.Ticket as TicketDomain

@Entity
@Table(name = "ticket")
class Ticket(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "ticket_no", unique = true)
    val ticketNo: String,
    @Column(name = "perf_id")
    val performanceInfoId: Long,
    @Column(name = "perf_schedule_id")
    val performanceScheduleId: Long,
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val status: TicketStatus = TicketStatus.APPROVED,
    @Column(name = "refunded_at")
    val refundedAt: LocalDateTime? = null,
    @OneToMany(mappedBy = "ticket", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val _ticketItems: MutableList<TicketItem> = mutableListOf(),
) : BaseTimeEntity() {
    val ticketItems: List<TicketItem>
        get() = _ticketItems.toList()

    fun addTicketItems(items: List<TicketItem>) {
        _ticketItems.clear()
        _ticketItems.addAll(items)
    }

    companion object {
        fun create(ticketDomain: TicketDomain): Ticket {
            return Ticket(
                userId = ticketDomain.userId,
                ticketNo = ticketDomain.ticketNo,
                performanceInfoId = ticketDomain.performanceId,
                performanceScheduleId = ticketDomain.scheduleId,
            ).apply {
                addTicketItems(
                    ticketDomain.ticketItems.map { domainItem ->
                        TicketItem(
                            seatId = domainItem.seatId,
                            ticketItemNo = domainItem.ticketItemNo,
                            ticket = this,
                        )
                    },
                )
            }
        }
    }
}
