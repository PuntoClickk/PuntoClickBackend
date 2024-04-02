package com.puntoclick.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.puntoclick.features.login.route.JWTParams
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*


fun Application.configureSecurity() {

    val jwtParams = getJWTParams()

    authentication {
        jwt("auth-jwt") {
            realm = jwtParams.realm
            verifier(JWT
                .require(Algorithm.HMAC256(jwtParams.secret))
                .withAudience(jwtParams.audience)
                .withIssuer(jwtParams.issuer)
                .build())
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }

}

fun Application.getJWTParams(): JWTParams {
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val realm = environment.config.property("jwt.realm").getString()
    return JWTParams(audience, issuer, secret, realm)
}

