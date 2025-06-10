package com.cd18.domain.member.repository

import com.cd18.domain.member.dto.MemberDto

interface MemberRepository {
    fun getById(userId: Long): MemberDto
}
