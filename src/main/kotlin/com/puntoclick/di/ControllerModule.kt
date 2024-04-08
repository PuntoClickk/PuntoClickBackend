package com.puntoclick.di

import com.puntoclick.features.roles.controller.RoleController
import com.puntoclick.features.team.controller.TeamController
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val controllerModule = module {
    singleOf(::RoleController)
    singleOf(::TeamController)
}
