package com.puntoclick.di

import com.puntoclick.features.roles.controller.RoleController
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val controllerModule = module {
    singleOf(::RoleController)
}