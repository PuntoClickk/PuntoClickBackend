package com.puntoclick.features.team.route

import com.puntoclick.data.model.UUIDAppRequest
import com.puntoclick.data.model.team.UpdateTeamRequest
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

        post("/") {
            val request = call.receive<UUIDAppRequest>()
            val result = teamController.getTeam(request.id)
            call.respond(message = result.handleResult(), status= result.status)
        }

        post("/update") {
            val team : UpdateTeamRequest = call.receive()
            val result = teamController.updateTeam(team)
            call.respond(message = result.handleResult() , status = result.status )
        }

        post("/delete") {
            val uuidAppRequest : UUIDAppRequest = call.receive()
            val result = teamController.deleteTeam(uuidAppRequest.id)
            call.respond(message = result.handleResult() , status = result.status)
        }
    }
}
