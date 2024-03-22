package com.puntoclick.features.roles.route

import com.puntoclick.features.roles.database.RoleDaoFacade
import com.puntoclick.features.roles.entity.Role
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.roleRouting(dao : RoleDaoFacade){
    route("/role"){
        post("/add") {
            val result = dao.addRole("test")
            call.respond(result.toString())
        }
    }
}