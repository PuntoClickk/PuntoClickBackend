package com.puntoclick

import com.puntoclick.data.DatabaseFactory
import com.puntoclick.data.DatabaseFactory.init
import com.puntoclick.features.roles.database.RoleDaoFacade
import com.puntoclick.features.roles.database.RoleDaoFacadeImp
import com.puntoclick.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {

    val dao : RoleDaoFacade = RoleDaoFacadeImp()
    init()

    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureDatabases()
    configureSecurity()
    configureRouting(dao)
}

