package com.cd18.infra.persistence.repository

import com.cd18.common.exception.BaseException
import com.cd18.domain.common.page.PageParam
import com.cd18.domain.performance.dto.PerformanceInfoDetailDto
import com.cd18.domain.performance.dto.PerformanceInfoDto
import com.cd18.domain.performance.dto.PerformancePriceDto
import com.cd18.domain.performance.enums.PerformanceInfoErrorCode
import com.cd18.domain.performance.repository.PerformanceInfoRepository
import com.cd18.infra.persistence.config.applyPagination
import com.cd18.infra.persistence.model.QPerformanceDiscount.performanceDiscount
import com.cd18.infra.persistence.model.QPerformanceInfo.performanceInfo
import com.cd18.infra.persistence.model.QPerformancePrice.performancePrice
import com.cd18.infra.persistence.repository.extensions.leftJoinPerformanceDiscount
import com.cd18.infra.persistence.repository.extensions.leftJoinPerformancePrice
import com.cd18.infra.persistence.repository.jpa.PerformanceInfoJpaRepository
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class PerformanceInfoRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
    private val performanceInfoJpaRepository: PerformanceInfoJpaRepository,
) : PerformanceInfoRepository {
    override fun isExistById(id: Long): Boolean {
        return performanceInfoJpaRepository.existsById(id)
    }

    override fun getList(pageParam: PageParam): List<PerformanceInfoDto> {
        return queryFactory.select(
            Projections.constructor(
                PerformanceInfoDto::class.java,
                performanceInfo.id,
                performanceInfo.name,
                performanceInfo.venue,
                performancePrice.price,
                performanceDiscount.discountPrice,
                performanceInfo.startDate,
                performanceInfo.endDate,
            ),
        ).from(performanceInfo)
            .leftJoinPerformancePrice()
            .leftJoinPerformanceDiscount()
            .orderBy(performanceInfo.id.desc())
            .applyPagination(pageParam)
            .fetch()
    }

    override fun getById(id: Long): PerformanceInfoDetailDto {
        return queryFactory.select(
            Projections.constructor(
                PerformanceInfoDetailDto::class.java,
                performanceInfo.id,
                performanceInfo.name,
                performanceInfo.description,
                performanceInfo.venue,
                performancePrice.price,
                performanceDiscount.discountPrice,
                performanceInfo.startDate,
                performanceInfo.endDate,
            ),
        ).from(performanceInfo)
            .leftJoinPerformancePrice()
            .leftJoinPerformanceDiscount()
            .where(performanceInfo.id.eq(id))
            .fetchFirst()
            ?: throw BaseException(PerformanceInfoErrorCode.NOT_FOUND)
    }

    override fun getPriceInfoByPerformanceId(performanceId: Long): PerformancePriceDto {
        return queryFactory.select(
            Projections.constructor(
                PerformancePriceDto::class.java,
                performanceInfo.id,
                performancePrice.id,
                performancePrice.price,
                performanceDiscount.id,
                performanceDiscount.discountPrice,
            ),
        ).from(performanceInfo)
            .leftJoinPerformancePrice()
            .leftJoinPerformanceDiscount()
            .where(performanceInfo.id.eq(performanceId))
            .fetchFirst()
            ?: throw BaseException(PerformanceInfoErrorCode.NOT_FOUND)
    }

    override fun updateDiscountPrice(
        performanceId: Long,
        performanceDiscountId: Long,
        discountPrice: Int,
    ) {
        queryFactory.update(performanceDiscount)
            .set(performanceDiscount.discountPrice, discountPrice)
            .set(performanceDiscount.updatedAt, LocalDateTime.now())
            .where(
                performanceDiscount.performanceInfoId.eq(performanceId),
                performanceDiscount.id.eq(performanceDiscountId),
            )
            .execute()
    }
}
