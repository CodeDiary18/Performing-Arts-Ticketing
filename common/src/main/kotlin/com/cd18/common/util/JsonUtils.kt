package com.cd18.common.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.oshai.kotlinlogging.KotlinLogging

object JsonUtils {
    private val logger = KotlinLogging.logger {}
    private val objectMapper =
        ObjectMapper()
            .registerModule(KotlinModule.Builder().build())
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    fun prettyPrintJson(json: String): String {
        return try {
            val jsonNode = objectMapper.readTree(json)
            objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode)
        } catch (e: Exception) {
            json
        }
    }

    fun <T> parse(
        payload: String?,
        clazz: Class<T>,
    ): T? {
        if (payload.isNullOrBlank()) {
            logger.warn { "❌ Payload is null or blank for ${clazz.simpleName}" }
            return null
        }

        return try {
            objectMapper.readValue(payload, clazz).also {
                logger.debug { "✅ Parsed ${clazz.simpleName} successfully" }
            }
        } catch (e: JsonProcessingException) {
            logger.warn(e) { "❌ Invalid JSON format for ${clazz.simpleName}" }
            null
        } catch (e: IllegalArgumentException) {
            logger.warn(e) { "❌ Invalid argument for ${clazz.simpleName}" }
            null
        } catch (e: Exception) {
            logger.error(e) { "❌ Unexpected error while parsing ${clazz.simpleName}" }
            null
        }
    }

    inline fun <reified T> parse(payload: String?): T? = parse(payload, T::class.java)

    fun <T> parseToResult(
        payload: String?,
        clazz: Class<T>,
    ): Result<T> {
        if (payload.isNullOrBlank()) {
            return Result.failure(IllegalArgumentException("Payload is null or blank"))
        }

        return try {
            Result.success(objectMapper.readValue(payload, clazz))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    inline fun <reified T> parseToResult(payload: String?): Result<T> = parseToResult(payload, T::class.java)
}
