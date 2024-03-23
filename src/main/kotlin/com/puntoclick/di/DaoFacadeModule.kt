package com.puntoclick.di

import com.puntoclick.data.database.roledaofacade.RoleDaoFacade
import com.puntoclick.data.database.roledaofacade.RoleDaoFacadeImp
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val daoFacadeModule = module {
    singleOf(::RoleDaoFacadeImp) {
        bind<RoleDaoFacade>()
    }
}