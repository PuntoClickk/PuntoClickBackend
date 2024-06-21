package com.puntoclick.features.invitation.controller

import com.puntoclick.data.database.invitation.daofacade.InvitationDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.invitation.CreateInvitationData
import com.puntoclick.data.model.invitation.InvitationResponse
import com.puntoclick.features.utils.*
import io.ktor.http.*
import java.util.*

class InvitationController(
    private val invitationDaoFacade: InvitationDaoFacade
) {

    suspend fun createInvitation(teamId: UUID?, locale: Locale): AppResult<InvitationResponse> {
        if (teamId == null) return locale.createError(descriptionKey = StringResourcesKey.CODE_GENERATION_FAILED_ERROR_KEY)

        val createInvitationData = CreateInvitationData(getNewCode(), getExpiration(), teamId)
        val result = invitationDaoFacade.createInvitation(createInvitationData)

        return if (result != null) AppResult.Success(data = result, appStatus = HttpStatusCode.OK)
        else locale.createError(descriptionKey = StringResourcesKey.CODE_GENERATION_FAILED_ERROR_KEY)
    }

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