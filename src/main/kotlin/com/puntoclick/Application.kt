package com.puntoclick


import com.puntoclick.data.database.configureDatabase
import com.puntoclick.features.utils.configureGlobalLocale
import com.puntoclick.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureKoin()
    configureRateLimit()
    configureDatabase()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureSecurity()
    configureGlobalLocale()
    configureRequestValidation()
    configureRouting()
}



