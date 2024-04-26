package com.puntoclick.features.team.controller

import com.puntoclick.data.database.entity.Team
import com.puntoclick.data.database.team.daofacade.TeamDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.features.team.model.CreateTeamRequest
import com.puntoclick.features.team.model.UpdateTeamRequest
import com.puntoclick.features.utils.*
import io.ktor.http.*
import java.util.UUID

class TeamController(
    private val facade: TeamDaoFacade
) {
    suspend fun getTeam(id: UUID) = tryCatch {
        val team = searchTeam(id = id)
        team?.let {
            AppResult.Success(
                data = it, appStatus = HttpStatusCode.OK
            )
        } ?: createError(title = NOT_FOUND_OBJECT_TITLE, NOT_FOUND_OBJECT_DESCRIPTION, HttpStatusCode.NotFound)
    }

    suspend fun addTeam(createTeamRequest: CreateTeamRequest): AppResult<Boolean> = tryCatch {
        val validatedName = createTeamRequest.name.validateRequestString()
        validatedName?.let {
            createTeam(createTeamRequest)
        } ?: createError(title = "NError", "Desc Error", HttpStatusCode.BadRequest)
    }

    suspend fun updateTeam(updateTeamRequest: UpdateTeamRequest):AppResult<Boolean> = tryCatch {
        val validatedName = updateTeamRequest.name.validateRequestString()
        validatedName?.let {
            updateTeamName(updateTeamRequest)
        } ?: createError(title = "Not valid", "Desc Error", HttpStatusCode.BadRequest)
    }

    suspend fun deleteTeam(id: UUID): AppResult<Boolean> = tryCatch {
        val result = facade.deleteTeam(id)
        if (result) {
            AppResult.Success(
                data = true,
                appStatus = HttpStatusCode.OK
            )
        } else {
            createError(
                title = NOT_FOUND_OBJECT_TITLE,
                description = NOT_FOUND_OBJECT_DESCRIPTION,
                status = HttpStatusCode.NotFound
            )
        }
    }


    private suspend fun searchTeam(id: UUID): Team? {
        return facade.team(id)
    }

    private suspend fun createTeam(createTeamRequest: CreateTeamRequest): AppResult<Boolean> =
        if (facade.addTeam(createTeamRequest)) {
            AppResult.Success(data = true, appStatus = HttpStatusCode.OK)
        } else createError(title = "NError", "Desc Error", HttpStatusCode.BadRequest)


    private suspend fun updateTeamName(updateTeamRequest: UpdateTeamRequest): AppResult<Boolean> =
        if (facade.updateTeam(updateTeamRequest)) {
            AppResult.Success(data = true, appStatus = HttpStatusCode.OK)
        } else createError(title = NOT_FOUND_OBJECT_TITLE, NOT_FOUND_OBJECT_DESCRIPTION, HttpStatusCode.NotFound)

}
