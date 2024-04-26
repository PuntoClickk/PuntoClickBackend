package com.puntoclick.data.database.team.daofacade

import com.puntoclick.data.database.entity.Team
import com.puntoclick.features.team.model.CreateTeamRequest
import com.puntoclick.features.team.model.UpdateTeamRequest
import java.util.*

interface TeamDaoFacade {
    suspend fun allTeams(): List<Team>
    suspend fun team(uuid: UUID): Team?
    suspend fun addTeam(createTeamRequest: CreateTeamRequest): Boolean
    suspend fun updateTeam(updateTeamRequest: UpdateTeamRequest): Boolean
    suspend fun deleteTeam(uuid: UUID): Boolean
}