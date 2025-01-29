package com.cd18.web.config

import com.cd18.common.http.errorhandling.GlobalExceptionHandler
import com.cd18.common.http.response.CurrentUserResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebApiConfig : WebMvcConfigurer {
    @Bean
    fun globalExceptionHandler() = GlobalExceptionHandler()

    @Bean
    fun currentUserResolver() = CurrentUserResolver()

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(currentUserResolver())
    }
}
