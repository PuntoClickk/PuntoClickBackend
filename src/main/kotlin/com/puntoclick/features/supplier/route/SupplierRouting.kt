package com.puntoclick.features.supplier.route

import com.puntoclick.data.model.GlobalLocale
import com.puntoclick.data.model.UUIDAppRequest
import com.puntoclick.data.model.supplier.CreateSupplierRequest
import com.puntoclick.data.model.supplier.UpdateSupplierRequest
import com.puntoclick.data.utils.ROLE_IDENTIFIER
import com.puntoclick.data.utils.TEAM_IDENTIFIER
import com.puntoclick.data.utils.USER_IDENTIFIER
import com.puntoclick.features.supplier.controller.SupplierController
import com.puntoclick.features.utils.handleResult
import com.puntoclick.plugins.getIdentifier
import com.puntoclick.security.AppEncryption
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Route.supplierRouting() {

    val supplierController by inject<SupplierController>()
    val appEncryption by inject<AppEncryption> (qualifier = named("AES"))

    route("/supplier") {

        post("/add") {
            val request = call.receive<CreateSupplierRequest>()
            val user = call.getIdentifier(appEncryption, USER_IDENTIFIER)
            val team = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val role = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val locale = GlobalLocale.locale
            val result = supplierController.addSupplier(locale, request, user, role, team)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/all") {
            val team = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val role = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val locale = GlobalLocale.locale
            val result = supplierController.allSuppliers(locale, role, team)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/") {
            val request = call.receive<UUIDAppRequest>()
            val team = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val role = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val locale = GlobalLocale.locale
            val result = supplierController.getSupplier(locale, request.id, role, team)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/update") {
            val request : UpdateSupplierRequest = call.receive()
            val team = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val role = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val locale = GlobalLocale.locale
            val result = supplierController.updateSupplier(locale, request, role, team)
            call.respond(message = result.handleResult(), status = result.status)
        }

        post("/delete") {
            val request = call.receive<UUIDAppRequest>()
            val team = call.getIdentifier(appEncryption, TEAM_IDENTIFIER)
            val role = call.getIdentifier(appEncryption, ROLE_IDENTIFIER)
            val locale = GlobalLocale.locale
            val result = supplierController.deleteSupplier(locale, request.id, role, team)
            call.respond(message = result.handleResult(), status = result.status)
        }
    }
}