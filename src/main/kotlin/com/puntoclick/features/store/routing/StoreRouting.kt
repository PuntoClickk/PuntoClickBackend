package com.puntoclick.features.store.routing

import com.puntoclick.data.model.UUIDAppRequest
import com.puntoclick.data.model.store.CreateStoreRequest
import com.puntoclick.data.model.store.UpdateStoreRequest
import com.puntoclick.data.utils.ROLE_IDENTIFIER
import com.puntoclick.data.utils.TEAM_IDENTIFIER
import com.puntoclick.data.utils.USER_IDENTIFIER
import com.puntoclick.features.store.controller.StoreController
import com.puntoclick.features.utils.handleResult
import com.puntoclick.features.utils.retrieveLocale
import com.puntoclick.plugins.getIdentifier
import com.puntoclick.security.AppEncryption
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject


fun Route.storeRouting() {

    val storeController by inject<StoreController>()
    val appEncryption by inject<AppEncryption>(qualifier = named("AES"))

    route("/store") {
        post("/add") {
            val request = call.receive<CreateStoreRequest>()
            val locale = call.retrieveLocale()
            val userId = call.getIdentifier(appEncryption, USER_IDENTIFIER)
            val teamId = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val roleId = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val result = storeController.createStore(request, locale, userId, roleId, teamId)
            call.respond(message = result.handleResult(), status = result.status)
        }

        get("/with-user-names") {
            val locale = call.retrieveLocale()
            val teamId = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val roleId = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val result = storeController.getStoresWithUserNamesByTeam(teamId, locale, roleId)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/update") {
            val request = call.receive<UpdateStoreRequest>()
            val locale = call.retrieveLocale()
            val teamId = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val roleId = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val result = storeController.updateStore(request, locale, roleId, teamId)
            call.respond(message = result.handleResult(), status = result.status)
        }

        delete ("/delete") {
            val request = call.receive<UUIDAppRequest>()
            val locale = call.retrieveLocale()
            val teamId = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val roleId = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val result = storeController.deleteStore(request, locale, roleId, teamId)
            call.respond(message = result.handleResult(), status = result.status)
        }
    }

}