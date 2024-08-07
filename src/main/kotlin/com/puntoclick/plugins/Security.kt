package com.puntoclick.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.puntoclick.data.model.ErrorResponse
import com.puntoclick.data.utils.ROLE_IDENTIFIER
import com.puntoclick.data.utils.TEAM_IDENTIFIER
import com.puntoclick.data.utils.USER_IDENTIFIER
import com.puntoclick.security.AppEncryption
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import java.security.KeyFactory
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*


fun Application.configureSecurity() {
    val jwtParams = getJWTParams()

    authentication {
        jwt("auth-jwt") {
            realm = jwtParams.realm

            verifier(
                JWT
                    .require(Algorithm.ECDSA256(loadECPublicKey(jwtParams.public)))
                    .withIssuer(jwtParams.issuer)
                    .build()
            )

            validate { credential ->
                if (!credential.payload.getClaim(USER_IDENTIFIER).asString().isNullOrEmpty() &&
                    !credential.payload.getClaim(TEAM_IDENTIFIER).asString().isNullOrEmpty() &&
                    !credential.payload.getClaim(ROLE_IDENTIFIER).asString().isNullOrEmpty()
                    ) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(message = ErrorResponse("Error","Token is not valid or has expired"), status = HttpStatusCode.Unauthorized)
            }
        }

    }

}

fun Application.getJWTParams(): JWTParams {
    val private = environment.config.property("jwt.private").getString()
    val public = environment.config.property("jwt.public").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val realm = environment.config.property("jwt.realm").getString()

    return JWTParams(audience, issuer, realm, private, public)
}


fun loadECPrivateKey(key: String): ECPrivateKey {
    val keySpec = PKCS8EncodedKeySpec(Base64.getDecoder().decode(key))
    val keyFactory = KeyFactory.getInstance("EC")
    return keyFactory.generatePrivate(keySpec) as ECPrivateKey
}


fun loadECPublicKey(key: String): ECPublicKey {
    val publicKeyBytes = Base64.getDecoder().decode(key)
    val keySpec = X509EncodedKeySpec(publicKeyBytes)
    val keyFactory = KeyFactory.getInstance("EC")
    return keyFactory.generatePublic(keySpec) as ECPublicKey
}


data class JWTParams(
    val audience: String,
    val issuer: String,
    val realm: String,
    val private: String,
    val public: String
)


fun ApplicationCall.getIdentifier(appEncryption: AppEncryption, claimName: String): UUID {
    val principal = principal<JWTPrincipal>()
    val uuid = principal?.payload?.getClaim(claimName)?.asString()?.let {
        val uuid = appEncryption.decrypt(it)
        UUID.fromString(uuid)
    }
    return uuid ?: throw Exception("Token Error")
}
