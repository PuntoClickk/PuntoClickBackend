package com.puntoclick.features.team.route

import com.puntoclick.data.database.entity.Team
import com.puntoclick.data.model.AppRequest
import com.puntoclick.data.model.UUIDAppRequest
import com.puntoclick.features.team.controller.TeamController
import com.puntoclick.features.utils.handleResult
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Route.teamRouting() {

    val teamController by inject<TeamController>()

    route("/team"){

        post("/add") {
            val request = call.receive<AppRequest<String>>()
             val result = teamController.addTeam(request.data)
            call.respond(message = result.handleResult(), status= result.status)
        }

        post("/") {
            val request = call.receive<AppRequest<UUIDAppRequest>>()
            val result = teamController.getTeam(request.data.id)
            call.respond(message = result.handleResult(), status= result.status)
        }

        post("/update") {
            val request : AppRequest<Team> = call.receive()
            val team = request.data
            val result = teamController.updateTeam(team.id, name = team.name)
            call.respond(message = result.handleResult() , status = result.status )
        }

        post("/delete") {
            val request : AppRequest<UUIDAppRequest> = call.receive()
            val result = teamController.deleteTeam(request.data.id)
            call.respond(message = result.handleResult() , status = result.status)
        }
    }
}
