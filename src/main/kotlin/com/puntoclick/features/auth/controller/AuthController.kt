package com.puntoclick.features.auth.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.puntoclick.data.database.role.daofacade.RoleDaoFacade
import com.puntoclick.data.database.team.daofacade.TeamDaoFacade
import com.puntoclick.data.database.user.daofacade.UserDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.auth.LoginRequest
import com.puntoclick.data.model.auth.TokenResponse
import com.puntoclick.data.model.role.RoleType
import com.puntoclick.data.model.user.CreateUserRequest
import com.puntoclick.data.model.user.UserLogin
import com.puntoclick.data.model.user.mapCreateUserRequestToUser
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

    suspend fun createUser(createUserRequest: CreateUserRequest, locale: Locale): AppResult<Boolean> {
        val errorTitle = locale.loadLocalizedResources().getString(GENERIC_TITLE_ERROR)
        val errorMessage = locale.loadLocalizedResources().getString(USER_NOT_CREATED_ERROR)
        val roleUUID = roleDaoFacade.role(RoleType.ADMIN.value)?.id ?: return createError(title = errorTitle, errorMessage, HttpStatusCode.BadRequest)
        val teamUUID = teamDaoFacade.addTeam(createUserRequest.teamName) ?: return createError(title = errorTitle, errorMessage, HttpStatusCode.BadRequest)

        val user = createUserRequest.mapCreateUserRequestToUser(role = roleUUID, team = teamUUID)

        return if (userDaoFacade.addUser(user)) AppResult.Success(
            data = true, appStatus = HttpStatusCode.OK
        )
        else createError(title = errorTitle, errorMessage, HttpStatusCode.BadRequest)
    }

    suspend fun login(loginRequest: LoginRequest, jwtParams: JWTParams, locale: Locale): AppResult<TokenResponse> {
        val user = userDaoFacade.user(loginRequest.email)
        val errorTitle = locale.loadLocalizedResources().getString(GENERIC_TITLE_ERROR)
        val errorMessage = locale.loadLocalizedResources().getString(LOGIN_MESSAGE_ERROR_STRING_KEY)
        return user?.let {
            if (validatePassword(password = loginRequest.password, it.password)){
                AppResult.Success(data = createToken(it, jwtParams), appStatus = HttpStatusCode.OK)
            } else createError(
                title = errorTitle ,errorMessage
            )
        } ?: createError(title = errorTitle, errorMessage)
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


    private fun validatePassword(password: String,hashedPassword: String ): Boolean {
        return password.verifyPassword(hashedPassword = hashedPassword)
    }


}