package com.puntoclick.plugins

import com.puntoclick.di.controllerModule
import com.puntoclick.di.daoFacadeModule
import com.puntoclick.di.securityModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(
            daoFacadeModule,
            controllerModule,
            securityModule
        )
    }
}