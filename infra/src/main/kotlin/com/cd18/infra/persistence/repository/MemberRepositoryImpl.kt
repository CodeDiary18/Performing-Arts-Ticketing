package com.cd18.infra.persistence.repository

import com.cd18.common.exception.BaseException
import com.cd18.domain.member.dto.MemberDto
import com.cd18.domain.member.enums.MemberErrorCode
import com.cd18.domain.member.repository.MemberRepository
import com.cd18.infra.persistence.repository.jpa.MemberJpaRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
    private val memberJpaRepository: MemberJpaRepository,
) : MemberRepository {
    override fun getById(userId: Long): MemberDto {
        val member =
            memberJpaRepository.findById(userId)
                .orElseThrow { BaseException(MemberErrorCode.NOT_FOUND) }
        return MemberDto(id = member.id, name = member.name, email = member.email)
    }
}
