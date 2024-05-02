package com.puntoclick.di

import com.puntoclick.features.auth.controller.AuthController
import com.puntoclick.features.roles.controller.RoleController
import com.puntoclick.features.team.controller.TeamController
import com.puntoclick.features.user.controller.UserController
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val controllerModule = module {
    singleOf(::RoleController)
    singleOf(::TeamController)
    singleOf(::UserController)
    singleOf(::AuthController)
}
