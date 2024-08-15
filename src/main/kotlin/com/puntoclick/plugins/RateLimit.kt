package com.puntoclick.plugins

import com.puntoclick.data.utils.USER_IDENTIFIER
import com.puntoclick.security.AppEncryption
import io.ktor.server.application.*
import io.ktor.server.plugins.ratelimit.*
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject
import kotlin.time.Duration.Companion.seconds


fun Application.configureRateLimit() {

    val appEncryption by inject<AppEncryption>(qualifier = named("AES"))

    install(RateLimit) {
        register {
            rateLimiter(limit = 5, refillPeriod = 60.seconds)
        }
        register(RateLimitName(LimitType.PUBLIC.value)) {
            rateLimiter(limit = 10, refillPeriod = 60.seconds)
        }
        register(RateLimitName(LimitType.PROTECTED.value)) {
            rateLimiter(limit = 30, refillPeriod = 60.seconds)
            requestKey { applicationCall ->
                applicationCall.getIdentifier(appEncryption, USER_IDENTIFIER)
            }
            requestWeight { _, key ->
                when {
                    key.toString().isEmpty() -> 10
                    else -> 1
                }
            }
        }
    }
}


enum class LimitType(val value: String) {
    PUBLIC("public"),
    PROTECTED("protected")
}