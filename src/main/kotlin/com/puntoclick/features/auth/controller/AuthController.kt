package com.puntoclick.features.auth.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.puntoclick.data.database.role.daofacade.RoleDaoFacade
import com.puntoclick.data.database.team.daofacade.TeamDaoFacade
import com.puntoclick.data.database.user.daofacade.UserDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.auth.*
import com.puntoclick.data.model.role.RoleType
import com.puntoclick.data.model.user.UserLogin
import com.puntoclick.data.utils.ROLE_IDENTIFIER
import com.puntoclick.data.utils.TEAM_IDENTIFIER
import com.puntoclick.data.utils.USER_IDENTIFIER
import com.puntoclick.features.utils.*
import com.puntoclick.plugins.JWTParams
import com.puntoclick.plugins.loadECPrivateKey
import com.puntoclick.security.AppEncryption
import com.puntoclick.security.verifyPassword
import io.ktor.http.*
import java.util.*

class AuthController(
    private val userDaoFacade: UserDaoFacade,
    private val appEncryption: AppEncryption,
    private val teamDaoFacade: TeamDaoFacade,
    private val roleDaoFacade: RoleDaoFacade
) {

    suspend fun createAdmin(createUserRequest: CreateAdminRequest, locale: Locale): AppResult<Boolean> {
        if (userDaoFacade.emailExists(createUserRequest.email)) return getErrorEmailExists(locale)
        val roleUUID = roleDaoFacade.role(RoleType.ADMIN.value)?.id ?: return getErrorUserNotCreated(locale)
        val teamUUID = teamDaoFacade.addTeam(createUserRequest.teamName) ?: return getErrorUserNotCreated(locale)

        val user = createUserRequest.mapCreateUserRequestToUser(role = roleUUID, team = teamUUID)

        return if (userDaoFacade.addUser(user)) AppResult.Success(
            data = true, appStatus = HttpStatusCode.OK
        ) else locale.createGenericError()
    }

    suspend fun login(loginRequest: LoginRequest, jwtParams: JWTParams, locale: Locale): AppResult<TokenResponse> {
        val user = userDaoFacade.user(loginRequest.email)

        return user?.let {
            when {
                it.isActive.not() -> locale.createUserInactiveError()
                it.isBlocked -> locale.createUserBlockedError()
                validatePassword(password = loginRequest.password, it.password) -> {
                    AppResult.Success(data = createToken(it, jwtParams), appStatus = HttpStatusCode.OK)
                }

                else -> locale.createLoginError()
            }
        } ?: locale.createLoginError()
    }

    private fun createToken(userLogin: UserLogin, jwtParams: JWTParams): TokenResponse {
        jwtParams.apply {
            val algorithm = Algorithm.ECDSA256(loadECPrivateKey(jwtParams.private))
            val now = System.currentTimeMillis()
            val oneMonthInMillis = 30L * 24L * 60L * 60L * 1000L
            val encryptedId = appEncryption.encrypt(userLogin.id.toString())
            val encryptedTeamId = appEncryption.encrypt(userLogin.teamUUID.toString())
            val encryptedRoleId = appEncryption.encrypt(userLogin.roleUUID.toString())
            val token = JWT.create()
                .withIssuer(issuer)
                .withAudience(audience)
                .withClaim(USER_IDENTIFIER, encryptedId)
                .withClaim(TEAM_IDENTIFIER, encryptedTeamId)
                .withClaim(ROLE_IDENTIFIER, encryptedRoleId)
                .withExpiresAt(Date(now + oneMonthInMillis))
                .sign(algorithm)
            return TokenResponse(token)
        }
    }


    private fun validatePassword(password: String, hashedPassword: String): Boolean {
        return password.verifyPassword(hashedPassword = hashedPassword)
    }

    private fun getErrorUserNotCreated(locale: Locale) =
        locale.createError(
            descriptionKey = StringResourcesKey.USER_NOT_CREATED_ERROR_KEY,
            status = HttpStatusCode.BadRequest
        )

    private fun getErrorEmailExists(locale: Locale) =
        locale.createError(
            StringResourcesKey.GENERIC_TITLE_ERROR_KEY,
            StringResourcesKey.EMAIL_MESSAGE_ERROR_KEY,
            HttpStatusCode.BadRequest
        )


    private fun Locale.createUserInactiveError() = createError(
        StringResourcesKey.GENERIC_TITLE_ERROR_KEY,
        StringResourcesKey.LOGIN_USER_INACTIVE_MESSAGE_ERROR_KEY
    )

    private fun Locale.createUserBlockedError() = createError(
        StringResourcesKey.GENERIC_TITLE_ERROR_KEY,
        StringResourcesKey.LOGIN_USER_BLOCKED_MESSAGE_ERROR_KEY
    )

    private fun Locale.createLoginError() = createError(
        StringResourcesKey.GENERIC_TITLE_ERROR_KEY,
        StringResourcesKey.LOGIN_MESSAGE_ERROR_KEY
    )

}