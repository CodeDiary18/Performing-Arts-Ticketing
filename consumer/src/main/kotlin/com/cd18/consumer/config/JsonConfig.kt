package com.cd18.consumer.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class JsonConfig {
    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper().apply {
            // Kotlin module auto register
            findAndRegisterModules()

            // date/time handling
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)

            // null handling
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
        }
    }
}
