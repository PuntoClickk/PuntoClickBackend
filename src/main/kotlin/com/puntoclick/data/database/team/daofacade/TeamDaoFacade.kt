package com.puntoclick.data.database.team.daofacade

import com.puntoclick.data.database.entity.Team
import java.util.*

interface TeamDaoFacade {
    suspend fun allTeams(): List<Team>
    suspend fun team(uuid: UUID): Team?
    suspend fun addTeam(name: String): Boolean
    suspend fun updateTeam(team: Team): Boolean
    suspend fun deleteTeam(uuid: UUID): Boolean
}