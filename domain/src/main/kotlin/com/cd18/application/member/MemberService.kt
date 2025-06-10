package com.cd18.application.member

import com.cd18.domain.member.dto.MemberDto

interface MemberService {
    fun getById(id: Long): Result<MemberDto>
}
