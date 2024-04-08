package com.puntoclick.features.team.controller

import com.puntoclick.data.database.entity.Team
import com.puntoclick.data.database.team.daofacade.TeamDaoFacade
import com.puntoclick.data.model.AppResult
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

    suspend fun addTeam(name: String): AppResult<Boolean> = tryCatch {
        val validatedName = name.validateRequestString()
        if (validatedName == null ) createError(title = "NError", "Desc Error", HttpStatusCode.BadRequest)
        else createTeam(validatedName)
    }

    suspend fun updateTeam(id: UUID, name: String):AppResult<Boolean> = tryCatch {
        val validatedName = name.validateRequestString()
        if (validatedName == null ) createError(title = "NError", "Desc Error", HttpStatusCode.BadRequest)
        else updateTeamName(id, validatedName)
    }

    suspend fun deleteTeam(id: UUID) = tryCatch {
        val team = searchTeam(id = id)
        team?.let {
            AppResult.Success(
                data = it, appStatus = HttpStatusCode.OK
            )
        } ?: createError(title = NOT_FOUND_OBJECT_TITLE, NOT_FOUND_OBJECT_DESCRIPTION, HttpStatusCode.NotFound)
    }

    private suspend fun searchTeam(id: UUID): Team? {
        return facade.team(id)
    }

    private suspend fun createTeam(name: String): AppResult<Boolean> =
        if (facade.addTeam(name)) {
            AppResult.Success(
                data = true, appStatus = HttpStatusCode.OK
            )
        } else createError(title = "NError", "Desc Error", HttpStatusCode.BadRequest)


    private suspend fun updateTeamName(id: UUID, name: String): AppResult<Boolean> {
        val team = searchTeam(id = id)
        return team?.let {
            facade.updateTeam(it.copy(name = name))
            AppResult.Success(
                data = true, appStatus = HttpStatusCode.OK
            )
        } ?: createError(title = NOT_FOUND_OBJECT_TITLE, NOT_FOUND_OBJECT_DESCRIPTION, HttpStatusCode.NotFound)
    }


}