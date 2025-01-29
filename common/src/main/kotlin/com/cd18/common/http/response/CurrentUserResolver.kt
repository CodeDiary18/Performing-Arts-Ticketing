package com.cd18.common.http.response

import com.cd18.common.http.annotation.CurrentUser
import com.cd18.common.http.enums.Auth
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class CurrentUserResolver : HandlerMethodArgumentResolver {
    private val logger = KotlinLogging.logger {}

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(CurrentUser::class.java) &&
            parameter.parameterType in setOf(Long::class.java, Long::class.javaObjectType)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        val userId = webRequest.getHeader(Auth.X_USER_ID)?.toLongOrNull()
        val required = parameter.getParameterAnnotation(CurrentUser::class.java)?.required

        if (required == true && userId == null) {
            throw IllegalArgumentException("User ID is required but not found in the request header")
        }

        logger.debug { "userId: $userId" }
        return userId
    }
}
