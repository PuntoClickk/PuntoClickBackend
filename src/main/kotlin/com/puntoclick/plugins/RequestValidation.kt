package com.puntoclick.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*


fun Application.configureRequestValidation(){

    install(RequestValidation){
    }

    install(StatusPages){
        exception<RequestValidationException>(){ call, cause ->}
    }


}