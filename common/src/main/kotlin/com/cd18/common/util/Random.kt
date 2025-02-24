package com.cd18.common.util

import org.apache.commons.lang3.RandomStringUtils

fun generateRandomString(
    size: Int = 10,
    includeDigits: Boolean = true,
    includeAlphabet: Boolean = true,
    uppercase: Boolean = false,
): String {
    require(size in 1..50) { "Size must be between 1 and 50" }

    val randomString =
        when {
            includeAlphabet && includeDigits -> RandomStringUtils.randomAlphanumeric(size)
            includeAlphabet -> RandomStringUtils.randomAlphabetic(size)
            includeDigits -> RandomStringUtils.randomNumeric(size)
            else -> throw IllegalStateException("Unexpected condition")
        }

    return if (uppercase) randomString.uppercase() else randomString
}
