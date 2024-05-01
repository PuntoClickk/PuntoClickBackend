package com.puntoclick.features.team.controller

import com.puntoclick.data.database.team.daofacade.TeamDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.data.utils.*
import com.puntoclick.data.model.team.CreateTeamRequest
import com.puntoclick.data.model.team.TeamResponse
import com.puntoclick.data.model.team.UpdateTeamRequest
import com.puntoclick.features.utils.*
import io.ktor.http.*
import java.util.UUID

class TeamController(
    private val facade: TeamDaoFacade
) {
    suspend fun getTeam(id: UUID): AppResult<TeamResponse> {
        val team = searchTeam(id = id)
        return team?.let {
            AppResult.Success(
                data = it, appStatus = HttpStatusCode.OK
            )
        } ?: createError(title = ERROR_TITLE, GET_REGISTER, HttpStatusCode.NotFound)
    }

    suspend fun addTeam(createTeamRequest: CreateTeamRequest): AppResult<Boolean> {
        return createTeam(createTeamRequest)
    }

    suspend fun updateTeam(updateTeamRequest: UpdateTeamRequest):AppResult<Boolean> {
        return updateTeamName(updateTeamRequest)
    }

    suspend fun deleteTeam(id: UUID): AppResult<Boolean> {
        val result = facade.deleteTeam(id)
        return if (result) {
            AppResult.Success(
                data = true,
                appStatus = HttpStatusCode.OK
            )
        } else {
            createError(
                title = ERROR_TITLE,
                description = DELETE_ERROR_MESSAGE,
                status = HttpStatusCode.NotFound
            )
        }
    }


    private suspend fun searchTeam(id: UUID): TeamResponse? {
        return facade.team(id)
    }

    private suspend fun createTeam(createTeamRequest: CreateTeamRequest): AppResult<Boolean> =
        if (facade.addTeam(createTeamRequest)) {
            AppResult.Success(data = true, appStatus = HttpStatusCode.OK)
        } else createError(title = ERROR_TITLE, CREATE_BODY, HttpStatusCode.BadRequest)


    private suspend fun updateTeamName(updateTeamRequest: UpdateTeamRequest): AppResult<Boolean> =
        if (facade.updateTeam(updateTeamRequest)) {
            AppResult.Success(data = true, appStatus = HttpStatusCode.OK)
        } else createError(title = ERROR_TITLE, UPDATE_BODY, HttpStatusCode.NotFound)

}
