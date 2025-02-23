package com.cd18.common.exception

import com.cd18.common.http.response.code.ErrorCode

fun <T> Result<T>.transformBaseException(errorCode: ErrorCode): Result<T> = recoverCatching { throw BaseException(errorCode) }
