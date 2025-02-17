package com.cd18.common.exception

import com.cd18.common.http.response.code.BaseErrorCode
import com.cd18.common.http.response.code.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class MissingUserInfoHeaderException : RuntimeException() {
    val errorCode: ErrorCode = BaseErrorCode.UNAUTHORIZED
}
