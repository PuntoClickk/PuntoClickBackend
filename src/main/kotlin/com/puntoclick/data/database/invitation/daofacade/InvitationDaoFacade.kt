package com.puntoclick.data.database.invitation.daofacade

import com.puntoclick.data.model.invitation.CreateInvitationData
import com.puntoclick.data.model.invitation.InvitationData
import com.puntoclick.data.model.invitation.InvitationResponse

interface InvitationDaoFacade {
    suspend fun createInvitation(createInvitationData: CreateInvitationData): InvitationResponse?
    suspend fun invitationCodeExists(code: String): Boolean
    suspend fun getInvitationByCode(code: String): InvitationData?
    suspend fun deleteInvitationByCode(code: String): Boolean
}
