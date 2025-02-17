package com.cd18.common.http.errorhandling

import com.cd18.common.exception.BaseException
import com.cd18.common.exception.MissingUserInfoHeaderException
import com.cd18.common.http.response.code.BaseErrorCode
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(BaseException::class)
    fun handleBaseException(e: BaseException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(e.errorCode.status.value())
            .body(ErrorResponse.of(e.errorCode))
    }

    @ExceptionHandler(MissingUserInfoHeaderException::class)
    fun handleMissingUserInfoHeaderException(e: MissingUserInfoHeaderException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(e.errorCode.status.value())
            .body(ErrorResponse.of(e.errorCode))
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any> {
        val errorMessage =
            ex.bindingResult.fieldErrors
                .joinToString(", ") { it.defaultMessage ?: "Invalid value" }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.of(code = BaseErrorCode.INVALID_PARAMETER.code, message = errorMessage))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(BaseErrorCode.INTERNAL_SERVER_ERROR.status.value())
            .body(ErrorResponse.of(code = e.cause.toString(), message = e.message.toString()))
    }
}
