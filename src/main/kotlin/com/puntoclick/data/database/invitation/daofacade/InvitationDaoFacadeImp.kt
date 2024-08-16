package com.puntoclick.data.database.invitation.daofacade

import com.puntoclick.data.database.dbQuery
import com.puntoclick.data.database.invitation.table.InvitationTable
import com.puntoclick.data.model.invitation.CreateInvitationData
import com.puntoclick.data.model.invitation.InvitationData
import com.puntoclick.data.model.invitation.InvitationResponse
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class InvitationDaoFacadeImp: InvitationDaoFacade {

    override suspend fun createInvitation(createInvitationData: CreateInvitationData): InvitationResponse? = dbQuery {
       InvitationTable.insert {
            it[team] = createInvitationData.teamId
            it[expiresAt] = createInvitationData.expiresAt
            it[code] = createInvitationData.code
        }.resultedValues?.singleOrNull()?.let {
            resultRow ->
            resultRowToInvitationResponse(resultRow)
       }
    }


    override suspend fun invitationCodeExists(code: String): Boolean = dbQuery {
        InvitationTable.selectAll().where { InvitationTable.code eq code }.count() > 0
    }

    override suspend fun getInvitationByCode(code: String): InvitationData? = dbQuery {
        InvitationTable.selectAll().where { InvitationTable.code eq code }.singleOrNull()?.let {
                resultRow ->
            resultRowToInvitationData(resultRow)
        }
    }

    override suspend fun deleteInvitationByCode(code: String): Boolean  = dbQuery{
        InvitationTable.deleteWhere { InvitationTable.code eq  code } > 0
    }

    private fun resultRowToInvitationResponse(row: ResultRow) = InvitationResponse(
        code = row[InvitationTable.code],
        expiresAt = row[InvitationTable.expiresAt]
    )

    private fun resultRowToInvitationData(row: ResultRow) = InvitationData(
        teamId = row[InvitationTable.team],
        code = row[InvitationTable.code],
        expiresAt = row[InvitationTable.expiresAt]
    )

}
