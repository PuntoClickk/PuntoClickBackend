package com.puntoclick.data.database.invitation.daofacade

import com.puntoclick.data.model.invitation.CreateInvitationData
import com.puntoclick.data.model.invitation.InvitationResponse

interface InvitationDaoFacade {
    suspend fun createInvitation(createInvitationData: CreateInvitationData): InvitationResponse?
    suspend fun invitationCodeExists(code: String): Boolean
}
