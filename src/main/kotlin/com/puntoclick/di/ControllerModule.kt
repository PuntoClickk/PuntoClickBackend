package com.puntoclick.di

import com.puntoclick.features.roles.controller.RoleController
import io.ktor.server.application.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.ext.inject

val controllerModule = module {
    singleOf(::RoleController)
}

fun Application.configureControllers(): Controllers {
    val roleController by inject<RoleController>()
    return Controllers(roleController)
}

data class Controllers(
    val roleController: RoleController
)