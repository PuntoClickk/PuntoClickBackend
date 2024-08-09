package com.puntoclick.features.store.routing

import com.puntoclick.features.store.controller.StoreController
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Route.storeRouting() {

    val storeController by inject<StoreController>()

    route("/store"){

        post("/add"){

        }
    }
}