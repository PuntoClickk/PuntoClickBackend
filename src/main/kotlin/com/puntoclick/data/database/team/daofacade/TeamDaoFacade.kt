package com.puntoclick.data.database.team.daofacade

import com.puntoclick.data.model.team.CreateTeamRequest
import com.puntoclick.data.model.team.TeamResponse
import com.puntoclick.data.model.team.UpdateTeamRequest
import java.util.*

interface TeamDaoFacade {
    suspend fun allTeams(): List<TeamResponse>
    suspend fun team(uuid: UUID): TeamResponse?
    suspend fun addTeam(createTeamRequest: CreateTeamRequest): Boolean
    suspend fun updateTeam(updateTeamRequest: UpdateTeamRequest): Boolean
    suspend fun deleteTeam(uuid: UUID): Boolean
}