package com.cd18.common.util

import com.fasterxml.jackson.databind.ObjectMapper

object JsonUtils {
    private val objectMapper = ObjectMapper()

    fun prettyPrintJson(json: String): String {
        return try {
            val jsonNode = objectMapper.readTree(json)
            objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode)
        } catch (e: Exception) {
            json
        }
    }
}
