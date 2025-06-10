package com.cd18.domain.member.enums

import com.cd18.common.http.response.code.ErrorCode
import org.springframework.http.HttpStatus

enum class MemberErrorCode(
    override val message: String,
    override val status: HttpStatus,
) : ErrorCode {
    NOT_FOUND("사용자 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ;

    override val prefix: String = "MEMBER"
}
