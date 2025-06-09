package com.cd18.infra.persistence.init

import com.cd18.infra.persistence.model.Member
import com.cd18.infra.persistence.model.PerformanceDiscount
import com.cd18.infra.persistence.model.PerformanceInfo
import com.cd18.infra.persistence.model.PerformancePrice
import com.cd18.infra.persistence.model.PerformanceSchedule
import com.cd18.infra.persistence.model.Seat
import com.cd18.infra.persistence.repository.jpa.MemberJpaRepository
import com.cd18.infra.persistence.repository.jpa.PerformanceDiscountJpaRepository
import com.cd18.infra.persistence.repository.jpa.PerformanceInfoJpaRepository
import com.cd18.infra.persistence.repository.jpa.PerformancePriceJpaRepository
import com.cd18.infra.persistence.repository.jpa.PerformanceScheduleJpaRepository
import com.cd18.infra.persistence.repository.jpa.SeatJpaRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Component
class DummyDataInitializer(
    @Value("\${load-dummy-data.enabled:false}") private val isDummyInsertStr: String,
    private val memberJpaRepository: MemberJpaRepository,
    private val performanceInfoJpaRepository: PerformanceInfoJpaRepository,
    private val performanceScheduleJpaRepository: PerformanceScheduleJpaRepository,
    private val performancePriceJpaRepository: PerformancePriceJpaRepository,
    private val performanceDiscountJpaRepository: PerformanceDiscountJpaRepository,
    private val seatJpaRepository: SeatJpaRepository,
) : CommandLineRunner {
    private val logger = KotlinLogging.logger {}

    override fun run(vararg args: String?) {
        if (!isDummyInsertStr.toBoolean()) {
            return
        }
        insertMemberData()
        insertPerformanceInfoData()
    }

    private fun insertPerformanceInfoData() {
        if (performanceInfoJpaRepository.count() > 0) {
            logger.debug { "PerformanceInfo data already exists." }
            return
        }

        val performanceInfos =
            (1..20).map {
                val startDate = LocalDate.now().plusDays((10..50).random().toLong())
                PerformanceInfo(
                    name = "공연 $it",
                    description = "공연 $it 내용",
                    venue = getRandomVenue(),
                    startDate = startDate,
                    endDate = startDate.plusDays((50..100).random().toLong()),
                )
            }
        performanceInfoJpaRepository.saveAll(performanceInfos)

        val performanceInfo = performanceInfos[0]
        val performanceSchedules =
            performanceScheduleJpaRepository.saveAll(
                createPerformanceScheduleList(
                    performanceInfo.id,
                    performanceInfo.startDate,
                    performanceInfo.endDate,
                ),
            )
        val (performancePrice, performanceDiscount) = createPerformancePriceAndDiscount(performanceInfo.id)
        performancePriceJpaRepository.save(performancePrice)
        performanceDiscountJpaRepository.save(performanceDiscount)

        performanceSchedules.forEach { performanceSchedule ->
            val seats =
                createSeatList(
                    performanceId = performanceInfo.id,
                    performanceScheduleId = performanceSchedule.id,
                    numOfSeats = 500,
                )
            seatJpaRepository.saveAll(seats)
        }
    }

    private fun createPerformanceScheduleList(
        performanceId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<PerformanceSchedule> {
        val randomDays = DayOfWeek.entries.shuffled().take(2).toSet()

        val targetDate =
            (0L..ChronoUnit.DAYS.between(startDate, endDate))
                .map { startDate.plusDays(it) }
                .filter { it.dayOfWeek in randomDays }

        return targetDate.map {
            PerformanceSchedule(
                performanceInfoId = performanceId,
                startTime = it.atTime((18..20).random(), 0),
            )
        }
    }

    private fun createPerformancePriceAndDiscount(performanceId: Long): Pair<PerformancePrice, PerformanceDiscount> {
        val price = (80..200).random() * 1000

        val discountRate = listOf(0, 5, 10, 15, 20).random()
        val discountPrice = (price * (discountRate / 100.0)).toInt()

        val performancePrice =
            PerformancePrice(
                performanceInfoId = performanceId,
                price = price,
            )

        val performanceDiscount =
            PerformanceDiscount(
                performanceInfoId = performanceId,
                discountPrice = discountPrice,
            )

        return performancePrice to performanceDiscount
    }

    private fun createSeatList(
        performanceId: Long,
        performanceScheduleId: Long,
        numOfSeats: Int,
    ): List<Seat> {
        val seatsPerSection = 80 // 각 구역에 최대 80석
        val rowsPerSection = 10 // 구역당 10행
        val seatsPerRow = seatsPerSection / rowsPerSection // 구역당 1행에 포함되는 좌석 수
        val sectionsCount = (numOfSeats + seatsPerSection - 1) / seatsPerSection // 구역 수
        val sections = ('A'..'Z').take(sectionsCount) // A부터 Z까지 알파벳으로 구역을 지정

        return (1..numOfSeats).map {
            val sectionIndex = (it - 1) / seatsPerSection
            val section = sections[sectionIndex]
            val seatIndexInSection = (it - 1) % seatsPerSection
            val rowIndex = seatIndexInSection / seatsPerRow
            val seatIndexInRow = seatIndexInSection % seatsPerRow

            Seat(
                performanceInfoId = performanceId,
                performanceScheduleId = performanceScheduleId,
                seatName = "$section%02d".format(it),
                posX = seatIndexInRow + 1,
                posY = rowIndex + 1,
            )
        }
    }

    private fun insertMemberData() {
        val members =
            listOf("홍길동", "김철수", "이영희", "박명수", "정형돈", "유재석", "박명수", "정준하", "노홍철", "하하").map {
                Member(name = it, email = "test${it.indexOf(it)}@example.com")
            }
        memberJpaRepository.saveAll(members)
    }

    private fun getRandomVenue(): String {
        return listOf(
            "서울 대극장",
            "부산 대극장",
            "대구 대극장",
            "광주 대극장",
            "인천 대극장",
            "대전 대극장",
            "울산 대극장",
            "세종 대극장",
            "경기 대극장",
            "강원 대극장",
            "충북 대극장",
            "충남 대극장",
            "전북 대극장",
            "전남 대극장",
            "경북 대극장",
            "경남 대극장",
            "제주 대극장",
        ).random()
    }
}
