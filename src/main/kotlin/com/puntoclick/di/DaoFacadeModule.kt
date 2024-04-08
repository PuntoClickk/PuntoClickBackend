package com.puntoclick.di

import com.puntoclick.data.database.role.daofacade.RoleDaoFacade
import com.puntoclick.data.database.role.daofacade.RoleDaoFacadeImp
import com.puntoclick.data.database.team.daofacade.TeamDaoFacade
import com.puntoclick.data.database.team.daofacade.TeamDaoFacadeImp
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val daoFacadeModule = module {
    singleOf(::RoleDaoFacadeImp) {
        bind<RoleDaoFacade>()
    }
    singleOf(::TeamDaoFacadeImp) {
        bind<TeamDaoFacade>()
    }
}