package com.cd18.application.member.impl

import com.cd18.application.member.MemberService
import com.cd18.domain.member.dto.MemberDto
import com.cd18.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
) : MemberService {
    override fun getById(id: Long): Result<MemberDto> =
        runCatching {
            memberRepository.getById(id)
        }
}
