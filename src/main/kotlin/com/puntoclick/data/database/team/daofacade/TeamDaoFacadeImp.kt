package com.puntoclick.data.database.team.daofacade

import com.puntoclick.data.database.dbQuery
import com.puntoclick.data.database.team.table.TeamTable
import com.puntoclick.data.model.team.TeamResponse
import com.puntoclick.data.model.team.UpdateTeamRequest
import com.puntoclick.features.utils.escapeSingleQuotes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class TeamDaoFacadeImp : TeamDaoFacade {
    override suspend fun allTeams(): List<TeamResponse> = dbQuery {
        TeamTable.selectAll().map(::resultRowToTeam)
    }

    override suspend fun team(uuid: UUID): TeamResponse? = dbQuery {
        TeamTable.selectAll().where { TeamTable.uuid eq uuid }.map(::resultRowToTeam).singleOrNull()
    }

    override suspend fun addTeam(name: String): UUID? = dbQuery {
        val resultRow = TeamTable.insert {
            it[this.name] = name.escapeSingleQuotes()
        }.resultedValues?.singleOrNull()

        resultRow?.get(TeamTable.uuid)
    }

    override suspend fun updateTeam(updateTeamRequest: UpdateTeamRequest): Boolean = dbQuery {
        TeamTable.update({
            TeamTable.uuid eq updateTeamRequest.id
        }) {
            it[name] = updateTeamRequest.name.escapeSingleQuotes()
        } > 0
    }

    override suspend fun deleteTeam(uuid: UUID): Boolean = dbQuery {
        TeamTable.deleteWhere { TeamTable.uuid eq uuid } > 0
    }

    private fun resultRowToTeam(row: ResultRow) = TeamResponse(
        id = row[TeamTable.uuid],
        name = row[TeamTable.name]
    )
}