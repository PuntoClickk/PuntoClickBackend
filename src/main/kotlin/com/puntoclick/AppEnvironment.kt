package com.puntoclick

import io.ktor.server.application.*


fun Application.getEnvironment(): AppEnvironment {
    val env = environment.config.propertyOrNull("ktor.environment")?.getString()
    return when (env) {
        "Development" -> AppEnvironment.DEV
        else -> AppEnvironment.PROD
    }
}

enum class AppEnvironment {
    DEV,
    PROD
}