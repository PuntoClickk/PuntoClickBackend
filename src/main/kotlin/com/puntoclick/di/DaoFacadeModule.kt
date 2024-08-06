package com.puntoclick.di

import com.puntoclick.data.database.invitation.daofacade.InvitationDaoFacade
import com.puntoclick.data.database.invitation.daofacade.InvitationDaoFacadeImp
import com.puntoclick.data.database.permission.daofacade.PermissionDaoFacade
import com.puntoclick.data.database.permission.daofacade.PermissionDaoFacadeImp
import com.puntoclick.data.database.role.daofacade.RoleDaoFacade
import com.puntoclick.data.database.role.daofacade.RoleDaoFacadeImp
import com.puntoclick.data.database.team.daofacade.TeamDaoFacade
import com.puntoclick.data.database.team.daofacade.TeamDaoFacadeImp
import com.puntoclick.data.database.user.daofacade.UserDaoFacade
import com.puntoclick.data.database.user.daofacade.UserDaoFacadeImp
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
    singleOf(::UserDaoFacadeImp) {
        bind<UserDaoFacade>()
    }
    singleOf(::InvitationDaoFacadeImp) {
        bind<InvitationDaoFacade>()
    }
    singleOf(::PermissionDaoFacadeImp) {
        bind<PermissionDaoFacade>()
    }
}