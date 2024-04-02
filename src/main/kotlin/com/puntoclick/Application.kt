package com.puntoclick


import com.auth0.jwk.JwkProviderBuilder
import com.puntoclick.data.database.configureDatabase
import com.puntoclick.di.configureControllers
import com.puntoclick.features.login.route.ParamsJWT
import com.puntoclick.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import java.util.concurrent.TimeUnit

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()


    configureKoin()

    val controllers = configureControllers()

    val jwtParams = ParamsJWT( audience, issuer, secret)

    configureDatabase()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureSecurity()
    configureRouting(controllers, jwtParams)
}

