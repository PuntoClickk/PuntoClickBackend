package com.puntoclick.data.database.team.daofacade

import com.puntoclick.data.database.dbQuery
import com.puntoclick.data.database.entity.Team
import com.puntoclick.data.database.team.table.TeamTable
import com.puntoclick.features.team.model.CreateTeamRequest
import com.puntoclick.features.team.model.UpdateTeamRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class TeamDaoFacadeImp : TeamDaoFacade {
    override suspend fun allTeams(): List<Team> = dbQuery {
        TeamTable.selectAll().map(::resultRowToTeam)
    }

    override suspend fun team(uuid: UUID): Team? = dbQuery {
        TeamTable.select {
            TeamTable.uuid eq uuid
        }.map(::resultRowToTeam).singleOrNull()
    }

    override suspend fun addTeam(createTeamRequest: CreateTeamRequest): Boolean = dbQuery {
        TeamTable.insert {
            it[name] = createTeamRequest.name
        }.resultedValues?.singleOrNull() != null
    }

    override suspend fun updateTeam(updateTeamRequest: UpdateTeamRequest): Boolean = dbQuery {
        TeamTable.update({ TeamTable.uuid eq updateTeamRequest.id}) { it[name] = updateTeamRequest.name } > 0
    }

    override suspend fun deleteTeam(uuid: UUID): Boolean = dbQuery {
        TeamTable.deleteWhere { TeamTable.uuid eq uuid } > 0
    }

    private fun resultRowToTeam(row: ResultRow) = Team(
        id = row[TeamTable.uuid],
        name = row[TeamTable.name],
        createdAt = row[TeamTable.createAt],
        lastUpdate = row[TeamTable.updateAt]
    )
}