package com.puntoclick.features.invitation.route

import com.puntoclick.data.utils.TEAM_IDENTIFIER
import com.puntoclick.data.utils.USER_IDENTIFIER
import com.puntoclick.features.invitation.controller.InvitationController
import com.puntoclick.features.utils.handleResult
import com.puntoclick.plugins.getIdentifier
import com.puntoclick.security.AppEncryption
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject


fun Route.invitationRouting() {
    val invitationController by inject<InvitationController>()
    val appEncryption by inject<AppEncryption>(qualifier = named("AES"))

    route("invitation") {
        post("/create") {
            val userId = call.getIdentifier(appEncryption, USER_IDENTIFIER)
            val teamId = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val result = invitationController.createInvitation(userId, teamId)
            call.respond(message = result.handleResult(), status = result.status)
        }
    }

}
