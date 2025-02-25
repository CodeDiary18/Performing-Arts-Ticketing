package com.cd18.application.ticketing

import com.cd18.application.ticketing.impl.SeatServiceImpl
import com.cd18.common.exception.BaseException
import com.cd18.domain.ticketing.enums.SeatStatus
import com.cd18.domain.ticketing.enums.TicketingErrorCode
import com.cd18.domain.ticketing.model.SeatHolding
import com.cd18.domain.ticketing.model.SeatLockGroup
import com.cd18.domain.ticketing.repository.SeatLockRepository
import com.cd18.domain.ticketing.repository.SeatRepository
import com.cd18.domain.ticketing.repository.TicketRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.platform.commons.logging.LoggerFactory
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import kotlin.test.Test
import kotlin.test.assertEquals

class SeatServiceTest {
    private val seatRepository: SeatRepository = mockk(relaxed = true)
    private val seatLockRepository: SeatLockRepository = mockk(relaxed = true)
    private val ticketRepository: TicketRepository = mockk(relaxed = true)
    private val seatService = SeatServiceImpl(seatRepository, seatLockRepository, ticketRepository)
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Test
    fun `Only one succeeds when reserving same seats at once`() {
        val userId1 = 1L
        val userId2 = 2L
        val performanceId = 1L
        val scheduleId = 1L
        val seatIds = listOf(1L, 5L)

        every {
            seatRepository.findAvailableSeatsWithLock(
                performanceId = performanceId,
                scheduleId = scheduleId,
                seatIds = seatIds,
            )
        } returns
            seatIds.map {
                SeatHolding(
                    seatId = it,
                    seatName = "A$it",
                    status = SeatStatus.AVAILABLE,
                )
            } andThen emptyList()

        val latch = CountDownLatch(2)
        val executor = Executors.newFixedThreadPool(2)
        val results = mutableListOf<Result<SeatLockGroup>>()

        repeat(2) { index ->
            executor.submit {
                try {
                    val result =
                        seatService.holdingSeats(
                            userId = if (index == 0) userId1 else userId2,
                            performanceId = performanceId,
                            scheduleId = scheduleId,
                            seatIds = seatIds,
                        )
                    synchronized(results) { results.add(result) }
                } catch (e: BaseException) {
                    synchronized(results) { results.add(Result.failure(e)) }
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()
        executor.shutdown()

        assertEquals(2, results.size)
        val successes = results.filter { it.isSuccess }
        assertEquals(1, successes.size)

        val failures = results.filter { it.isFailure }
        assertEquals(1, failures.size)
        assertEquals(TicketingErrorCode.ALREADY_SEAT_HOLD, (failures[0].exceptionOrNull() as BaseException).errorCode)

        verify(exactly = 1) {
            seatLockRepository.saveAllFromGroup(any())
            seatRepository.updateSeatStatus(seatIds, SeatStatus.HOLDING)
        }
    }
}
