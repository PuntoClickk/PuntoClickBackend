package com.puntoclick.features.invitation.controller

import com.puntoclick.data.database.invitation.daofacade.InvitationDaoFacade
import com.puntoclick.data.database.user.daofacade.UserDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.invitation.CreateInvitationData
import com.puntoclick.data.model.invitation.InvitationResponse
import com.puntoclick.data.model.role.RoleType
import com.puntoclick.features.utils.StringResourcesKey
import com.puntoclick.features.utils.createError
import io.ktor.http.*
import java.util.*

class InvitationController(
    private val invitationDaoFacade: InvitationDaoFacade,
    private val userDaoFacade: UserDaoFacade
) {

    suspend fun createInvitation(userId: UUID, teamId: UUID, locale: Locale): AppResult<InvitationResponse> {
        if (isUserValidToCreateInvitation(userId)) return locale.createError()
        val createInvitationData = CreateInvitationData(getNewCode(), getExpiration(), teamId)
        val result = invitationDaoFacade.createInvitation(createInvitationData)

        return if (result != null) AppResult.Success(data = result, appStatus = HttpStatusCode.OK)
        else locale.createError(descriptionKey = StringResourcesKey.CODE_GENERATION_FAILED_ERROR_KEY)
    }

    private suspend fun isUserValidToCreateInvitation(userId: UUID) =
        (userDaoFacade.user(userId)?.role?.type != RoleType.ADMIN.type)

    private suspend fun getNewCode(): String {
        var code: String
        do {
            code = generateRandomCode()
        } while (invitationDaoFacade.invitationCodeExists(code))
        return code
    }

    private fun generateRandomCode(): String {
        val alphanumericChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

        fun randomSegment(): String {
            val segment1 = (1..3).map { alphanumericChars.random() }.joinToString("")
            val segment2 = (1..3).map { alphanumericChars.random() }.joinToString("")
            val segment3 = (1..3).map { alphanumericChars.random() }.joinToString("")
            return "$segment1-$segment2-$segment3"
        }

        return randomSegment()
    }

    private fun getExpiration(): Long {
        val now = System.currentTimeMillis()
        val oneWeekInMillis = 7L * 24L * 60L * 60L * 1000L
        val expiresAt = now + oneWeekInMillis
        return expiresAt
    }

}