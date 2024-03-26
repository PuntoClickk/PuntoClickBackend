package com.puntoclick

import com.puntoclick.data.database.configureDatabase
import com.puntoclick.di.configureControllers
import com.puntoclick.firebase.FirebaseAdmin.configureFirebase
import com.puntoclick.firebase.configureFirebaseAuth
import com.puntoclick.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureKoin()

    val controllers = configureControllers()

    configureFirebase()
    configureFirebaseAuth()
    configureDatabase()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureSecurity()
    configureRouting(controllers)
}

